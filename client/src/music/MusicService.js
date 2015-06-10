(function() {
    'use strict';

    angular
        .module('music')
        .factory('MusicService', MusicService);

    function MusicService($http) {
        var baseUrl = '/';

        return {
            getPopularFrom: getPopularFrom
        };

        function getPopularFrom(ago) {
            var url = baseUrl + 'music/' + ago + '/popular';

            return $http.get(url);
        }
    }
})();