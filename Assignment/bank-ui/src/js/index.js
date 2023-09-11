(function() {
    'use strict';

    function AccountsConfig($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider
            .when('/login', {
                templateUrl: 'login/signup.html',
                controller: 'LoginController as $ctrl'
            })
            .when('/logout', {
                templateUrl: 'login/logout.html',
                controller: 'LoginController as $ctrl'
            })
            .when('/createAccount', {
                templateUrl: 'account/createAccount.html',
                controller: 'AccountController as $ctrl'
            })
            .when('/delete', {
                templateUrl: 'account/delete.html',
                controller: 'AccountController as $ctrl'
            })
            .when('/update', {
                  templateUrl: 'account/update.html',
                  controller: 'AccountController as $ctrl'
             })
            .when('/updatePage', {
                  templateUrl: 'account/updatePage.html',
                  controller: 'AccountController as $ctrl'
            })
			.when('/search', {
                templateUrl: 'account/search.html',
                controller: 'AccountController as $ctrl'
            })
			.when('/balances', {
                templateUrl: 'balance/balances.html',
                controller: 'AccountController as $ctrl'
            })
			.when('/transactions', {
                templateUrl: 'transaction/transaction.html',
                controller: 'AccountController as $ctrl'
            })
            .when('/UserSearch/:userId', {
                templateUrl: 'users/UserSearch.html',
                controller: 'AccountController as $ctrl'
            })
			.when('/UserBalance/:userId', {
                templateUrl: 'users/UserBalance.html',
                controller: 'AccountController as $ctrl'
            })
			.when('/UserTransactions/:userId', {
                templateUrl: 'users/UserTransaction.html',
                controller: 'AccountController as $ctrl'
            })
			.when('/createTransaction/:userId', {
                templateUrl: 'users/createTransaction.html',
                controller: 'AccountController as $ctrl'
            })
            .otherwise('/login');
    }

    angular.module('bank-ui', ['ngRoute', 'ngResource', 'ng'])
        .config(['$locationProvider', '$routeProvider', AccountsConfig]);

}());