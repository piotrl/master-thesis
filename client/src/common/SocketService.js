angular.module('common')
    .factory('mySocket', function (socketFactory) {
        return socketFactory();
    });