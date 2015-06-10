(function() {
    angular
        .module('app', ['ngMaterial', 'music'])
        .config(function($mdThemingProvider){
            $mdThemingProvider.theme('default')
                .primaryPalette('yellow')
                .accentPalette('green');

        });
})();