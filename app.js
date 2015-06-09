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

//require("./server/controllers/lastfm");
//require("./server/controllers/rescueTime");
//require("./server/controllers/matcher")();

httpServer.listen(port, function () {
    console.log('HTTP Server on ' + port);
});

function configureStaticServer(app) {
    var serveStatic = require('serve-static');

    app.use(serveStatic(__dirname + '/client'));
}