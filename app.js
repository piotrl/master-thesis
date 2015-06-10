'use strict';

var app  = require("express")();
var httpServer = require("http").Server(app);
var routes = require("./server/routes/index");
var io = require("socket.io")(httpServer);

var port = process.env.PORT || 3000;

var database = require('./server/config/database');
var db = database.connect();

configureStaticServer(app);
routes(app);

var lastfm = require("./server/controllers/lastfm");
var activityAggregator = require("./server/controllers/rescueTime");
var matcher = require("./server/controllers/matcher");

activityAggregator(function() {
    console.log('Activities aggregation finished');
    lastfm.aggregator(function() {
        console.log('Lastfm aggregation finished');
        matcher();
    });
});

io.sockets.on("connection", function (socket) {
    console.log("[Socket] connection");
    lastfm.streamer(io, socket).start();
});

httpServer.listen(port, function () {
    console.log('HTTP Server on ' + port);
});

function configureStaticServer(app) {
    var serveStatic = require('serve-static');

    app.use(serveStatic(__dirname + '/client'));
}