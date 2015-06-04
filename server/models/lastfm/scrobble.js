var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var schema = new Schema({
    mbid: String,
    name: String,
    artist: String,
    startTime: Date,
    endTime: Date,
    spentTime: Number
});

module.exports = mongoose.model("Scrobble", schema);