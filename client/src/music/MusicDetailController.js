(function() {
    'use strict';

    angular
        .module('music')
        .controller('MusicDetailController', MusicDetailController);

    function MusicDetailController($mdBottomSheet, track) {
        var vm = this;
        vm.track = track;
        console.log(track);

        this.hide = $mdBottomSheet.hide;
    }


})();