var lastfm = require('../config/lastfm').connect();
var moment = require('moment');
var Track = require('../models/lastfm/track')
var Scrobble = require('../models/lastfm/scrobble')

var username = 'grovman';
var endDate = moment();
var startDate = moment().subtract(30, 'day');

module.exports = {
    aggregator: aggregator,
    streamer: streamer
};

function streamer(io, socket) {
    var trackStream = lastfm.stream(username);

    trackStream.on('nowPlaying', function(track) {
        io.sockets.emit('nowPlaying', track);
        console.log('Now playing: ' + track.name);
    });

    trackStream.on('scrobbled', function(track) {
        io.sockets.emit('scrobbled', track);
        console.log('Scrobbled: ' + track.name);
    });

    trackStream.on('stoppedPlaying', function(track) {
        io.sockets.emit('stoppedPlaying', track);
        console.log('Stopped playing: ' + track.name);
    });

    return trackStream;
}

function aggregator(callback) {
    getLastScrobble(function(err, docs) {
        if (docs.length) {
            startDate = moment(docs[0]._doc.endTime);
        }
        if (endDate.diff(startDate) < 0) {
            getRecentTracksFromPage(1, function(page, totalPages) {
                while (page++ < totalPages) {
                    getRecentTracksFromPage(page, function(page) {
                        console.log("Aggregated [" + startDate.fromNow() + "] [Page " + page + " / " + totalPages + "]");
                    });
                }
            });
        }
        callback();
    });
};

function getLastScrobble(callback) {
    Scrobble
        .find({}, callback)
        .sort({ endTime: -1 })
        .limit(1);
}

function getRecentTracksFromPage(page, callback) {
    return getRecentTracks(
        startDate.unix(),
        endDate.unix(),
        page
    );

    function getRecentTracks(startDate, endDate, page) {
        return lastfm.request("user.getRecentTracks", {
            user: username,
            to: endDate,
            from: startDate,
            page: page || 1,
            limit: 200,
            handlers: {
                success: onDataLoaded
            }
        });
    }

    function onDataLoaded(data) {
        if (!data.recenttracks['@attr']) {
            return;
        }
        var page = parseInt(data.recenttracks['@attr'].page);
        var totalPages = parseInt(data.recenttracks['@attr'].totalPages);

        callback && callback(page, totalPages);
        data.recenttracks.track.forEach(saveTrackEntry);
    }
}

function saveTrackEntry(scrobbleInfo) {
    if (isPlayingNow(scrobbleInfo)) {
        return;
    }
    checkIfDataExistInDb(scrobbleInfo, function (trackInfo) {
        var scrobble = convertToScrobbleModel(scrobbleInfo, trackInfo);
        var findDuplicatesBy = {
            startTime: scrobble._doc.startTime
        };
        runIfNotExistInDb(Scrobble, findDuplicatesBy, scrobble.save)
    });
}

function isPlayingNow(scrobbleInfo) {
    return scrobbleInfo['@attr'] && scrobbleInfo['@attr'].nowplaying === "true";
}

function convertToScrobbleModel(scrobbleInfo, trackInfo) {
    var startTime = moment.unix(parseInt(scrobbleInfo.date.uts));
    var endTime = moment(startTime).add(trackInfo.duration, 's');

    return new Scrobble({
        mbid: trackInfo.mbid,
        name: trackInfo.name,
        artist: trackInfo.artist,
        startTime: startTime.toDate(),
        endTime: endTime.toDate(),
        spentTime: trackInfo.duration,
        productivity: initProductivity()
    });
}

function checkIfDataExistInDb(track, callback) {
    var queryBy = {};
    if (track.mbid.length) {
        queryBy.mbid = track.mbid;
    } else {
        queryBy.name = track.name;
        queryBy.artist = track.artist;
    }

    Track.find(queryBy, function(err, docs) {
        if (docs.length) {
            callback(docs[0]._doc)
        } else {
            getTrackInfo(
                track.name, track.artist['#text'], function(trackInfo) {
                    var trackModel = saveTrack(trackInfo);
                    callback(trackModel._doc);
                }
            );
        }
    }).limit(1);

    function getTrackInfo(trackName, artistName, callback) {
        lastfm.info('track', {
            track: trackName,
            artist: artistName,
            handlers: {
                success: callback,
                error: logError
            }
        });
    }

    function saveTrack(trackInfo) {
        var miliToSecondsDivider = 1000;
        var track = new Track({
            lastfmId: trackInfo.id,
            mbid: trackInfo.mbid,
            name: trackInfo.name,
            artist: trackInfo.artist.mbid,
            duration: trackInfo.duration / miliToSecondsDivider
        });

        var findDuplicatesBy = {
            lastfmId: trackInfo.id
        };

        runIfNotExistInDb(Track, findDuplicatesBy, track.save);

        return track;
    }
}

function runIfNotExistInDb(Model, queryBy, ifNotExist) {
    Model.find(queryBy, function(err, docs) {
        if (docs.length === 0) {
            ifNotExist();
        }
    }).limit(1);
}

function initProductivity() {
    return {
        productive: 0,
        unproductive: 0,
        neutral: 0
    };
}

function logError(error) {
    console.log("Error: " + error.message);
}