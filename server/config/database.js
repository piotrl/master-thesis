var mongoose = require('mongoose');

module.exports = {
    url: 'mongodb://localhost/appdb',
    connect: function() {
        return connectTo(this.url);
    }
};

/*
    mongodb://[<user>:<pass>@]mongo.domain.ext[:<port>]/Database
    default port: 27017
*/

function connectTo(url) {
    mongoose.connect(url);
    var db = mongoose.connection;

    db.on('open', function () {
        console.log('[MongoDB] Connected: ' + url);
    });

    db.on('error', console.error.bind(console, '[MongoDB] Error: '));

    return db;
}