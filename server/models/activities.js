var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var activitySchema = new Schema({
    startTime: Date,
    endTime: Date,
    spentTime: Number,
    Activity: String,
    Category: String,
    Productivity: Number
});

module.exports = mongoose.model("Activity", activitySchema);