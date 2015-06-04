var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var trackSchema = new Schema({
    mbid: String,
    name: String,
    artist: String,
    time: Number,
    startTime: Date,
    endTime: Date,
    spentTime: Number
});

module.exports = mongoose.model("Track", trackSchema);