var lastfm = require('../config/lastfm').connect();
var moment = require('moment');
var Track = require('../models/lastfm/track')
var Scrobble = require('../models/lastfm/scrobble')

var username = 'grovman';
var endDate = moment().unix();
var startDate = moment().subtract(1, 'day').unix();

var pages = 1;

var request = lastfm.request("user.getRecentTracks", {
    user: username,
    to: endDate,
    from: startDate,
    handlers: {
        success: function(data) {
            data.recenttracks.track.forEach(saveTrackEntry);
            pages = parseInt(data.recenttracks['@attr'].totalPages);
            console.log("Success: " + data);
        },
        error: function(error) {
            console.log("Error: " + error.message);
        }
    }
});

function saveTrackEntry(scrobbleInfo) {
    checkIfDataExistInDb(scrobbleInfo, function (trackInfo) {
        var scrobble = convertToScrobbleModel(scrobbleInfo, trackInfo);
        scrobble.save();
    });
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
        spentTime: trackInfo.duration
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
                track.name, track.artist['#text'], saveTrack
            );
        }
    }).limit(1);

    function getTrackInfo(trackName, artistName, callback) {
        lastfm.info('track', {
            track: trackName,
            artist: artistName,
            handlers: {
                success: function(data) {
                    callback(data);
                },
                error: function(error) {
                    console.log("Error: " + error.message);
                }
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

        track.save();
    }
}