var rest = require('rest');
var moment = require('moment');

var rescueTimeConfig = require('../config/rescueTime');
var Activity = require('../models/activities');

var endTime = new moment().subtract(30, 'd').toDate();

module.exports = function(callback) {
    getLastActivity(function(err, docs) {
        if (docs.length) {
            endTime = docs[0]._doc.endTime
        }
        aggregateActivities(endTime)
            .then(callback);
    });
};

function getLastActivity(callback) {
    Activity
        .find({}, callback)
        .sort({ endTime: -1 })
        .limit(1);
}

function aggregateActivities(last) {
    var from = new moment(last).add(1, 'd');
    var to = new moment().add(1, 'd');

    var promise = rest(rescueTimeConfig.dataUrl({
        restrict_begin: from.format('YYYY-MM-DD'),
        restrict_end: to.format('YYYY-MM-DD')
    }));

    if (to.diff(from) < 0) {
        console.log('No need to aggregate Activities');
        return promise;
    }

    return promise.then(function(response) {
        return JSON.parse(response.entity);
    })
    .then(function(response) {
        response.rows.forEach(function(row) {
            var activity = analyseRow(row);
            activity.save();
        })
    });
}
function analyseRow(row) {
    var startDate = new moment(row[0]);
    var spentTime = row[1];
    var endDate = new moment(startDate).add(spentTime, 'second');

    return new Activity({
        startTime: startDate.toDate(),
        endTime: endDate.toDate(),
        spentTime: spentTime,
        Activity: row[3],
        Category: row[4],
        Productivity: row[5],
    });
}