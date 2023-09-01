(function() {
    'use strict';

    function AccountsConfig($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.when('/login', {
            templateUrl: 'login/login.html',
            controller: 'LoginController as $ctrl'
        }).when('/logout', {
            templateUrl: 'login/logout.html',
            controller: 'LogoutController as $ctrl'
        }).when('/accounts', {
            templateUrl: 'accounts/account.html',
            controller: 'AccountController as $ctrl'
        }).when('/search', {
            templateUrl: 'accounts/search.html',
            controller: 'AccountController as $ctrl'
        }).otherwise('/search');
    }

    angular.module('news-fe', ['ngRoute', 'ngResource', 'ng'])
        .config(['$locationProvider', '$routeProvider', AccountsConfig]);

}());