(function () {
    'use strict';
    angular.module("news-fe", ['ngResource', 'ng']);

     function BalanceService($resource) {
            return $resource('balances/:id');
        }
  angular.module('news-fe').factory('BalanceService', ['$resource', BalanceService]);
function BalanceController(BalanceService) {
        var self = this;

        self.service = BalanceService;
        self.balances = [];
        self.date = '';
        self.display = false;

        self.init = function () {
            self.search();
        }

        self.search = function () {
            self.display = false;
            var parameters = {};
            if (self.date) {
                parameters.search =  '%' + self.date + '%'  ;
            }
            self.service.get(parameters).$promise.then(function (response) {
                self.display = true;
                self.balances = response.content;
            });
        }

        self.init();
    }
    angular.module("news-fe").controller('BalanceController', ['BalanceService', BalanceController]);

    }());