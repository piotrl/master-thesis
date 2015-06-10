'use strict';

require('array.prototype.find');

var moment = require('moment');
var Scrobble = require('../models/lastfm/scrobble');

exports.mostProductive = function(req, res) {
    var MAX_RESPONSE = 30;

    getFrom(req, mostProductive);

    function mostProductive(summedItems) {
        var response = summedItems.sort(function (a, b) {
            return b.productivity.productive - a.productivity.productive;
        }).slice(0, MAX_RESPONSE);

        res.json(response);
    }
};

exports.mostListened = function(req, res) {
    var MAX_RESPONSE = 30;

    getFrom(req, mostListened);

    function mostListened(summedItems) {
        var response = summedItems.sort(function (a, b) {
            return b.scrobbled - a.scrobbled;
        }).slice(0, MAX_RESPONSE);

        res.json(response);
    }
};

function getFrom(req, callback) {
    var daysAgo = {
        day: 1,
        week: 7,
        month: 30
    };

    daysAgo = daysAgo[req.params.ago] || 30;
    var fromDate = moment().subtract(daysAgo, 'days');

    var findBy = {
        startTime: {
            $gte: fromDate.toDate()
        }
    };

    Scrobble.find(findBy, function(err, docs) {
        docs = docs.map(getDoc);
        var groupedItems = groupBy(docs, "name");
        var summedItems = sum(groupedItems);

        callback(summedItems);

    });
}


function sum(groupedItems) {
    var defaultProd = {
        productive: 0,
        unproductive: 0,
        neutral: 0
    };

    return groupedItems.map(sumProductivity);

    function sumProductivity(item) {
        var result = {
            name: item.name,
            artist: item.artist,
            scrobbled: item.scrobbles.length
        };

        result.productivity = item.scrobbles.reduce(function(prev, curr) {
            prev.productive += curr.productive;
            prev.unproductive += curr.unproductive;
            prev.neutral += curr.neutral;

            return prev;
        }, clone(defaultProd));


        return result;
    }
}


function groupBy(docs, field) {
    var grouped = [];

    docs.forEach(function(doc) {
       var element = grouped.find(function(el) {
           return el[field] === doc[field];
       });
        if (element) {
            element.scrobbles.push(doc.productivity);
        } else {
            grouped.push(initItem(doc, field));
        }
    });

    return grouped;
}

function initItem(doc, groupByField) {
    var item = {
        name: doc.name,
        artist: doc.artist,
        spentTime: doc.spentTime,
        scrobbles: [doc.productivity]
    };
    item[groupByField] = doc[groupByField];

    return item;
}

function getDoc(doc) {
    return doc._doc;
}

function clone(obj) {
    return JSON.parse(JSON.stringify(obj))
}