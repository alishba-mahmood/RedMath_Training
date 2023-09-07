(function() {
    'use strict';


    function AccountService($resource) {
        return $resource('bank/api/v1/accounts/:extraPath/:id', { extraPath: '@extraPath', id: '@id' });
    }
    angular.module('bank-ui').factory('AccountService', ['$resource', AccountService]);

    function BalanceService($resource) {
        return $resource('bank/api/v1/balances/accountId', {  accountId: '@accountId' });
    }
    angular.module('bank-ui').factory('BalanceService', ['$resource', BalanceService]);

    function TransactionService($resource) {
        return $resource('bank/api/v1/transactions/:accountId', {  accountId: '@accountId' });
    }
    angular.module('bank-ui').factory('TransactionService', ['$resource', TransactionService]);

    function SharedService() {
            var account = {};
            var accountBalance = {};
            var loggedInUserAccountId;

            return this;
        }

    angular.module('bank-ui').factory('SharedService', [SharedService]);


    function AccountController($location, $routeParams, AccountService, BalanceService, TransactionService, SharedService) {

            var self = this;
            self.id;
            self.display = false;

           self.service = AccountService;
           self.balanceService = BalanceService;
           self.transactionService = TransactionService;
           self.sharedService = SharedService;

           self.accounts = [];
           self.name = '';
           self.accountItem = {};
           //self.sharedService.loggedInUserAccountId = $routeParams.userAccountId;

           self.init = function() {
                console.log("ac int");
           }


     self.loadAccounts = function () {
             console.log("loas");
            self.service.get().$promise.then(function (response) {
            self.display = true;
            console.log("response: "+response);
            self.accounts = response.content;
            }).catch(function (error) {
                $location.path('/login');
                console.error('hgiuhiuhui');
            });
     }

     self.loadAccounts();

     self.search = function() {
     console.log("in search");
                 var nameParam = '';
                 if (self.name) {
                    console.log("name written");
                     nameParam = self.name;
                 } else {
                     nameParam = '';
                 }
                 self.service.get({ extraPath: 'search', name: nameParam }).$promise.then(function(response) {
                     console.log(response);
                     self.accounts = response.content;
                     console.log(self.accounts);

                     })
                 }

   self.create = function()
    {
         console.log("in create");
         $location.path('/createAccount');
         console.log("move to create");
    }
    self.delete = function()
    {
        console.log("in delete");
        $location.path('/delete');
        console.log("move to delete");
    }
   self.update=function()
   {
        console.log("in update");
        $location.path('/update');
   }

   self.UpdateID = function(id)
   {
           console.log("move to update info");
           $location.path('/updatePage');
           console.log("move to update");
           self.service.get({ id: id }).$promise.then(function(response) {
           self.sharedService.account = response.content;
           });
   }


   self.save = function()
    {
            console.log(self.accountItem);
            self.service.save(self.accountItem).$promise.then(function(response) {
                console.log(self.accountItem);
                alert('account created successfully');
                $location.path('/search');
            })
            .catch(function (error) {
                if (error.status === 403)
                {
                      $location.path('/login');
                }
                alert('Error occured while creating account.');
                console.error('hgiuhiuhui');
            });
        }

    self.deleteAccount = function(id) {
              console.log('id to delete: '+id);
              self.service.delete({ extraPath: '', id: id }).$promise.then(function(response) {
                  alert('account deleted successfully');
                  self.loadAccounts();
                  $location.path('/search');
              })
              .catch(function (error) {
              if (error.status === 403)
              {
                     $location.path('/login');
              }
              if (error.status === 404)
              {
                     alert('Account-id dont exist.');
              }
              alert('Error occured while deleting account.');
               console.error('hgiuhiuhui');
              });
          }

    self.UpdateAccount = function(id) {
              console.log('id to update: '+id);
              self.service.save(self.sharedService.account).$promise.then(function(response)
              {
                      alert('account updated successfully');
                      $location.path('/search');
              })
              .catch(function (error) {
              if (error.status === 403)
              {
                     $location.path('/login');
              }
              if (error.status === 404)
              {
                     alert('Account-id dont exist.');
              }
              alert('Error occured while updating account.');
               console.error('hgiuhiuhui');
              });
          }

    self.updateAccount = function()
    {
          self.service.save(self.sharedService.account).$promise.then(function(response)
          {
                alert('account updated successfully');
                $location.path('/search');
          });
    }
}


angular.module("bank-ui").controller('AccountController', ['$location', '$routeParams', 'AccountService', 'BalanceService', 'TransactionService', 'SharedService', AccountController]);

    }());