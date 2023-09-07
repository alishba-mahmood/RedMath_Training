(function() {
    'use strict';

    function BankConfig($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.

        when('/create', {
            templateUrl: 'admin/createAccount.html',
            controller: 'AccountController as $ctrl'

        }).when('/delete', {
            templateUrl: 'admin/delete.html',
            controller: 'AccountController as $ctrl'

        }).when('/search', {
            templateUrl: 'admin/search.html',
            controller: 'AccountController as $ctrl'

        }).otherwise('/delete');
    }

    angular.module('news-fe', ['ngRoute', 'ngResource', 'ng'])
        .config(['$locationProvider', '$routeProvider', BankConfig]);

}());


