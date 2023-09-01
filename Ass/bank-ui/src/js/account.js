(function () {
    'use strict';
    angular.module("news-fe", ['ngResource', 'ng']);

     function AccountService($resource) {
            return $resource('api/accounts/:id');
        }
  angular.module('news-fe').factory('AccountService', ['$resource', AccountService]);
function AccountController(AccountService) {
        var self = this;

        self.service = AccountService;
        self.accounts = [];
        self.name = '';
        self.display = false;

        self.init = function () {
            self.search();
        }

        self.search = function () {
            self.display = false;
            var parameters = {};
            if (self.name) {
                parameters.search =  '%' + self.name + '%'  ;
            }
            self.service.get(parameters).$promise.then(function (response) {
                self.display = true;
                self.accounts = response.content;
            });
        }

        self.init();
    }
    angular.module("news-fe").controller('AccountController', ['AccountService', AccountController]);

    }());