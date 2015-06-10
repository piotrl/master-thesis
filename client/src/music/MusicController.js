(function() {
    'use strict';

    angular
        .module('music')
        .controller('MusicController', MusicController);

    function MusicController(MusicService, mySocket, $mdBottomSheet) {
        var vm = this;

        vm.menu = buildNavigation();
        vm.music = {};

        vm.getPopular = buildLoader('getPopularFrom');
        vm.getProductive = buildLoader('getProductiveFrom');
        vm.getUnProductive = buildLoader('getUnProductive');
        vm.getNeutral = buildLoader('getNeutral');

        vm.showDetails = showDetails;

        vm.getPopular();
        listenSocket();

        function listenSocket() {
            mySocket.emit('test');
            mySocket.on('nowPlaying', function() {
                console.log('nowPlaying');
            });
            mySocket.on('scrobbled', function() {
                console.log('scrobbled');
            });
            mySocket.on('stoppedPlaying', function() {
                console.log('stoppedPlaying');
            });

        }

        function buildLoader(method) {
            return function() {
                clear();
                loadMuiscSection(method);
            }
        }

        function loadMuiscSection(method) {
            clear();
            getMusic('day', method);
            getMusic('week', method);
            getMusic('month', method);
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
            });
        }
    }

    function buildNavigation() {
        return {
            items: [
                {
                    name: "Popular music",
                    method: "getPopular"
                },
                {
                    name: "Productive music",
                    method: "getProductive"
                },
                {
                    name: "Unproductive music",
                    method: "getUnProductive"
                },
                {
                    name: "Neutral music",
                    method: "getNeutral"
                }
            ]
        };
    }
})();