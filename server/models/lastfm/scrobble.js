var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var schema = new Schema({
    mbid: String,
    name: String,
    artist: String,
    startTime: Date,
    endTime: Date,
    spentTime: Number,
    activities: Number,
    productivity: {
        productive: Number,
        unproductive: Number,
        neutral: Number
    }
});

module.exports = mongoose.model("Scrobble", schema);