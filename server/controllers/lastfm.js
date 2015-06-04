var lastfm = require('../config/lastfm').connect();
var moment = require('moment');
var trackModel = require('../models/lastfm/track')

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

function saveTrackEntry(track) {
    checkIfDataExistInDb(track);
    if (track.mbid) {

    } else {
        track.name;
        track.artist;
    }
}

function checkIfDataExistInDb(track) {
    var queryBy = {};
    if (track.mbid.length) {
        queryBy.mbid = track.mbid;
    } else {
        queryBy.name = track.name;
        queryBy.artist = track.artist;
    }

    trackModel.find(queryBy, function(err, docs) {
        if (docs.length) {
        } else {
            lastfm.info('track', {
                handlers: {
                    success: function(data) {
                        console.log("Success: " + data);
                    },
                    error: function(error) {
                        console.log("Error: " + error.message);
                    }
                },
                track: track.name,
                artist: track.artist['#text']
            });
        }
    }).limit(1);

    function saveTrack(trackInfo, scrobbled) {
        new Track({
            lastfmId: trackInfo.id,
            mbid: trackInfo.mbid,
            name: trackInfo.name,
            artist: trackInfo.artist.mbid,
            duration: trackInfo.duration
        })
    }
}