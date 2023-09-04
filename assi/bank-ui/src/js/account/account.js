(function () {
    'use strict';
/*
    angular.module("news-fe", ['ngResource', 'ng']);
*/

     function AccountService($resource) {
            return $resource('api/accounts/:id', { id: '@id' });
        }
  angular.module('news-fe').factory('AccountService', ['$resource', AccountService]);
function AccountController(AccountService) {
        var self = this;

        self.service = AccountService;
        self.accounts = [];
        self.name = '';
        self.id;
        self.display = false;
        self.accountItem = {};

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

        self.save = function() {
            self.service.save(self.accountItem).$promise.then(function(response) {
                self.accountItem.id = response.content.id;
            });
        }

        self.delete = function() {
        router.delete('/api/accounts/:id', (req, res) => {
            const accId = req.params.id;
                db.deleteItem(accId)
                    .then(() => {
                        res.status(200).send(); // Send a success response
                    })
                    .catch((error) => {
                        res.status(500).json({ error: 'An error occurred while deleting the item' });
                    });
            });


        }
        self.init();
    }
    angular.module("news-fe").controller('AccountController', ['AccountService', AccountController]);

    }());