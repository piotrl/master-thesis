'use strict';

var app  = require("express")();
var httpServer = require("http").Server(app);
var routes = require("./server/routes/index");
var io = require("socket.io")(httpServer);

var port = process.env.PORT || 3000;

var database = require('./server/config/database');
var db = database.connect();
routes(app);

require("./server/controllers/lastfm");
//require("./server/controllers/rescueTime");

httpServer.listen(port, function () {
    console.log('HTTP Server on ' + port);
});

function configureStaticServer() {
    var serveStatic = require('serve-static');
    var oneDay = 86400000;

    app.use('/img', serveStatic(__dirname + '/public/img', { maxAge: oneDay }));
    app.use('/js/jquery.min.js', serveStatic(__dirname + '/bower_components/jquery/dist/jquery.min.js'));
    app.use('/js/jquery.min.map', serveStatic(__dirname + '/bower_components/jquery/dist/jquery.min.map'));
    app.use(serveStatic(__dirname + '/public'));
}