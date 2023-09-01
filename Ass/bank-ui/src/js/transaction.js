(function () {
    'use strict';
    angular.module("news-fe", ['ngResource', 'ng']);

     function TransactionService($resource) {
            return $resource('transactions/:id');
        }
  angular.module('news-fe').factory('TransactionService', ['$resource', TransactionService]);
function TransactionController(TransactionService) {
        var self = this;

        self.service = TransactionService;
        self.transactions = [];
        self.description = '';
        self.display = false;

        self.init = function () {
            self.search();
        }

        self.search = function () {
            self.display = false;
            var parameters = {};
            if (self.description) {
                parameters.search =  '%' + self.description + '%'  ;
            }
            self.service.get(parameters).$promise.then(function (response) {
                self.display = true;
                self.transactions = response.content;
            });
        }

        self.init();
    }
    angular.module("news-fe").controller('TransactionController', ['TransactionService', TransactionController]);

    }());