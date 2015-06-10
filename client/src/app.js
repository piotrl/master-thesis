(function() {
    angular
        .module('app', ['ngMaterial', 'btford.socket-io', 'common', 'music'])
        .config(function($mdThemingProvider){
            $mdThemingProvider.theme('default')
                .primaryPalette('yellow')
                .accentPalette('green');
        });
})();