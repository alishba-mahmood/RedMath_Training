(function() {
    'use strict';

    function AccountsConfig($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.

        when('/login', {
            templateUrl: 'login/signup.html',
            controller: 'LoginController as $lctrl'

        }).when('/logout', {
            templateUrl: 'login/logout.html',
            controller: 'LoginController as $lctrl'

        }).when('/create', {
            templateUrl: 'account/createAccount.html',
            controller: 'AccountController as $ctrl'

        }).when('/search', {
            templateUrl: 'account/search.html',
            controller: 'AccountController as $ctrl'

        }).when('/delete', {
                    templateUrl: 'account/delete.html',
                    controller: 'AccountController as $ctrl'

        }).otherwise('/search');
    }

    angular.module('news-fe', ['ngRoute', 'ngResource', 'ng'])
        .config(['$locationProvider', '$routeProvider', AccountsConfig]);

}());