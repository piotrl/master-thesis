var rest = require('rest');
var moment = require('moment');

var rescueTimeConfig = require('../config/rescueTime');
var Activity = require('../models/activities');

rest(rescueTimeConfig.dataUrl({
    restrict_begin: '2015-06-01'
}))
.then(function(response) {
    return JSON.parse(response.entity);
})
.then(function(response) {
    response.rows.forEach(function(row) {
        var activity = analyseRow(row);
        activity.save();
    })
});

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