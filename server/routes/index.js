'use strict';

var indexController = require('../controllers/index');

module.exports = function(app) {
    app.route('/music/:ago/popular')
        .get(indexController.mostListened);

    app.route('/music/:ago/productive')
        .get(indexController.mostProductive);
};