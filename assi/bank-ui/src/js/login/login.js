(function() {
    'use strict';

    function LoginController($http, $location) {
        var self = this;
        self.user = {};

        self.login = function() {
            $http.post('/login',
                'username=' + self.user.name + '&password=' + self.user.password, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }).then(self.loginSuccess, self.loginFailure);
        }

        self.loginSuccess = function(response) {
            $location.path('/accounts');
        }

        self.loginFailure = function(response) {
            self.user.error = response.data.message;
        }

        self.logout = function() {
            $http.post('/logout').then(self.logoutSuccess, self.logoutFailure);
        }

        self.logoutSuccess = function(response) {
            $location.path('/search');
        }

        self.logoutFailure = function(response) {
            self.user.error = response.data.message;
        }
    }

    angular.module('news-fe').controller('LoginController', ['$http', '$location', LoginController]);

}());