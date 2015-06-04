var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var trackSchema = new Schema({
    lastfmId: String,
    mbid: String,
    name: String,
    artist: String,
    duration: Number
});

module.exports = mongoose.model("Track", trackSchema);