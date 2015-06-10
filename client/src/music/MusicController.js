(function() {
    'use strict';

    angular
        .module('music')
        .controller('MusicController', MusicController);

    function MusicController(MusicService, $mdBottomSheet, $log) {
        var vm = this;
        vm.menu = makeNavigation();
        vm.music = {};
        vm.getPopularMusic = getPopularMusic;
        vm.showDetails = showDetails;

        getPopularMusic('day');
        getPopularMusic('week');
        getPopularMusic('month');

        function getPopularMusic(timeAgo) {
            return MusicService.getPopularFrom(timeAgo)
                .then(function (music) {
                    console.log(music);
                    vm.music[timeAgo] = music.data;
                });
        }

        function showDetails(track) {

            return $mdBottomSheet.show({
                parent: angular.element(document.getElementById('content')),
                templateUrl: './src/music/view/productivitySheet.html',
                controller: 'MusicDetailController',
                controllerAs: "vm",
                bindToController: true,
                resolve: {
                    track: function() {
                        return track;
                    }
                }
            }).then(function (clickedItem) {
                clickedItem && $log.debug(clickedItem.name + ' clicked!');
            });
        }
    }

    function makeNavigation() {
        return {
            items: [
                {
                    name: "last month",
                    key: "month"
                }
            ]
        };
    }
})();