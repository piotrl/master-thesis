(function() {
    'use strict';

    angular
        .module('music')
        .controller('MusicController', MusicController);

    function MusicController(MusicService, $mdBottomSheet, $log) {
        var vm = this;
        vm.menu = makeNavigation();
        vm.music = {};
        vm.getPopularMusic = getPopular;
        vm.getProductiveMusic = getProductive;
        vm.showDetails = showDetails;
        getPopular();

        function getPopular() {
            clear();
            getPopularMusic('day');
            getPopularMusic('week');
            getPopularMusic('month');
        }

        function getProductive() {
            clear();
            getMusic('day', 'getProductiveFrom');
            getMusic('week', 'getProductiveFrom');
            getMusic('month', 'getProductiveFrom');
        }

        function getPopularMusic(timeAgo) {
            getMusic(timeAgo, 'getPopularFrom');
        }

        function getMusic(timeAgo, method) {
            return MusicService[method](timeAgo)
                .then(function (music) {
                    vm.music[timeAgo] = {
                        data: music.data,
                        header: timeAgo
                    };
                });
        }

        function clear() {
            vm.music = {};
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
                    name: "Popular music",
                    method: "getPopularMusic"
                },
                {
                    name: "Productive music",
                    method: "getProductiveMusic"
                }
            ]
        };
    }
})();