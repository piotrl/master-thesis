var Activity = require('../models/activities');
var Scrobble = require('../models/lastfm/scrobble');

var productivityType = {
    2: 'productive',
    1: 'neutral',
    0: 'unproductive'
};

module.exports = function() {
    getAllActivities(function(err, activities) {
        activities.forEach(function(activity) {
            findListenedMusic(activity._doc);
        });
    });
};


function getAllActivities(callback) {
    Activity.find({}, callback);
}

function findListenedMusic(activity) {
    var byStartTime = {
        startTime: {
            $lte: activity.endTime
        },
        endTime: {
            $gte: activity.startTime
        }
    };

    Scrobble.find(byStartTime, function (err, docs) {
        //console.log(docs.length + " scrobbles in " + activity.Activity);
        docs.forEach(calcProductivityProcentage);
    });

    function calcProductivityProcentage(scrobble) {
        var activityTime ={
            start: activity.startTime,
            end: activity.endTime,
        };
        var scrobbleTime = {
            start: scrobble._doc.startTime,
            end: scrobble._doc.endTime
        };

        var timeSpentOnActivity = calcTimeSpentOnActivity(activityTime, scrobbleTime, scrobble._doc.spentTime);
        addProductivityTime(activity.Productivity, scrobble, timeSpentOnActivity);
    }

    function calcTimeSpentOnActivity(activityTime, scrobbleTime, spentTime) {
        if (scrobbleTime.start < activityTime.start) {
            return spentTime - (scrobbleTime.start/1000 - activityTime.start/1000);
        } else if (scrobbleTime.end <= activityTime.end) {
            return spentTime;
        } else {
            return spentTime - (activityTime.end/1000 - scrobbleTime.end/1000);
        }
    }
}

function addProductivityTime(activityProductivity, scrobble, seconds) {
    var type = productivityType[activityProductivity];

    scrobble._doc.productivity[type] += seconds;
    if (scrobble._doc.activities) {
        scrobble._doc.activities += 1;
    } else {
        scrobble._doc.activities = 1;
    }

    Scrobble.update({
        _id: scrobble._doc._id
    }, scrobble._doc, {multi: false}, function(err, nr) {
        //console.log(nr);
    });
}