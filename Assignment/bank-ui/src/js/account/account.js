(function() {
    'use strict';


    function AccountService($resource) {

        return $resource('bank/api/v1/accounts/:extraPath/:id', { extraPath: '@extraPath', id: '@id' },
         {
                update:
                 {
                    method: 'PUT'
                 }
         });
    }
    angular.module('bank-ui').factory('AccountService', ['$resource', AccountService]);

    function BalanceService($resource) {
        return $resource('bank/api/v1/balances/:extraPath/:accountId', {  extraPath: '@extraPath', accountId: '@accountId' });
    }
    angular.module('bank-ui').factory('BalanceService', ['$resource', BalanceService]);

    function TransactionService($resource) {
        return $resource('bank/api/v1/transactions/:extraPath/:accountId', { extraPath: '@extraPath', accountId: '@accountId' });
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
            self.display = false;

           self.service = AccountService;
           self.balanceService = BalanceService;
           self.transactionService = TransactionService;
           self.sharedService = SharedService;

           self.accounts = [];
		   self.balances = [];
		   self.transactions = [];
		   self.userTransactions = [];

           self.id;
           self.userTranId;
           self.transactionId;
           self.userId = $routeParams.userId;
           self.name = '';

           self.accountItem = {};
		   self.balanceItem = {};
		   self.transactionItem = {};


           self.sharedService.loggedInUserAccountId = $routeParams.userId;
           self.userTranId=$routeParams.userId;

           self.init = function() {
           }


     self.loadAccounts = function () {
             console.log("load accounts");
            self.service.get().$promise.then(function (response)
			{
				self.display = true;
				self.accounts = response.content;
            }).catch(function (error)
			{
                $location.path('/login');
            });
     }
	 self.loadBalances = function ()
	 {
           self.balanceService.get().$promise.then(function (response)
           {
           		self.display = true;
           		self.balances = response.content;
           		console.log(self.balances);
           }).
           catch(function (error)
           {
                 $location.path('/login');
           });
     }

     self.getName = function (id)
     {

     }
	 self.loadTransactions = function ()
	 {
            self.transactionService.get().$promise.then(function (response)
			{
				self.display = true;

				self.transactions = response.content;
            }).catch(function (error) {
                $location.path('/login');
            });
     }

     //self.loadAccounts();

    self.search = function()
	{
     console.log("in search");
        var nameParam = '';
        if (self.name)
		{
            nameParam = self.name;
        }
		else
		{
            nameParam = '';
        }
        self.service.get({ extraPath: 'search', name: nameParam }).$promise.then(function(response)
		{
			self.accounts = response.content;
		})
    }

	self.searchBalance = function()
	{
        var nameParam = 0;
        if (self.id)
		{
            nameParam = self.id;
        }
		else
		{
            nameParam = 0;
        }
        self.balanceService.get({ extraPath: 'search', accountId: nameParam }).$promise.then(function(response)
		{
			self.balances = response.content;
		})
    }

	self.searchTransactions = function()
	{
        var nameParam = 0;
        if (self.id)
		{
            nameParam = self.id;
        }
		else
		{
            nameParam = 0;
        }
        self.balanceService.get({ extraPath: 'search', accountId: nameParam }).$promise.then(function(response)
		{
			self.transactions = response.content;
		})
    }

    self.create = function()
    {
         $location.path('/createAccount');
    }
	self.createNewTransaction = function()
    {
         self.userTranId=self.userId;
         $location.path('/createTransaction/' + self.userId);

    }
    self.delete = function()
    {
        $location.path('/delete');
    }
    self.update=function()
    {
        $location.path('/update');
    }

    self.UpdateID = function(id)
    {
               self.id = id;
               if (self.id === undefined)
               {
                   alert('Please enter ID to update.');
                   return; // Prevent form submission
               }
               var extraPath = self.id ? '' : null;
               self.service.get({ extraPath: extraPath, id: id }).$promise.then(function (response)
               {
                    self.sharedService.account = response.content;
                    $location.path('/updatePage');
               })
               .catch(function (error)
               {
                   if (error.status === 403)
                   {
                      self.UpdateID();
                  }
                  if (error.status === 500)
                  {
                      alert('Account-id Not Found');
                  }

               });

    }


    self.save = function()
    {
        if (self.accountItem.name === undefined)
        {
          alert('Please fill in NAME field.');
          return; // Prevent form submission
        }
        console.log(self.accountItem);
        self.service.save(self.accountItem).$promise.then(function(response)
		{
            alert('Account created successfully. Refresh the Page');
            $location.path('/search');
        })
        .catch(function (error)
		{
            if (error.status === 403)
            {
               self.save();
            }
            self.loadAccounts();
            $location.path('/search');
        });
    }

	self.saveTransaction = function(id)
    {
        if (self.transactionItem.amount === undefined ||
            self.transactionItem.db_CR === undefined ||
            self.transactionItem.description === undefined  )
            {
              alert('Please fill all the required fields.');
              return;
            }

            var currentDate = new Date();
            var year = currentDate.getFullYear();
            var month = String(currentDate.getMonth() + 1).padStart(2, '0');
            var day = String(currentDate.getDate()).padStart(2, '0');

            var formattedDate = year + '-' + month + '-' + day;

            self.transactionItem.account_id = id;
            self.transactionItem.balance_id = id;
            self.transactionItem.date=formattedDate;
            console.log(self.transactionItem);

            self.transactionService.get({ extraPath: 'getId' }).$promise.then(function(response)
            		{
            		    self.transactionId = response.content;

            			self.transactionItem.transaction_id = self.transactionId;
                                    console.log(self.transactionItem);
                                    self.transactionService.save(self.transactionItem).$promise.then(function(response)
                        		    {
                                        alert('transaction created successfully');
                        			    self.loadTransactions();
                                        $location.path('/UserBalance/'+ self.transactionItem.account_id);
                                    })
            		            .catch(function (error)
                      		    {
                      		    alert('Error occured while creating transaction. Create Transaction Again');
                                  self.loadTransactions();
                              });

               })
            .catch(function (error)
		    {
            alert('Error occured while creating transaction. Create Transaction Again');
            self.loadTransactions();
            $location.path('/userTransaction/'+ id);
        });
    }

    self.deleteAccount = function(id)
	{
        if (id === undefined)
        {
            alert('Please enter ID to update.');
            return; // Prevent form submission
        }
        self.service.delete({ extraPath: '', id: id }).$promise.then(function(response)
		{
			alert('account deleted successfully');
			self.loadAccounts();
			$location.path('/search');
        })
        .catch(function (error)
		{
            if (error.status === 403)
            {
			   alert('Only Admin has authority to delete account. Please sign-in again .');
               $location.path('/login');
            }
            if (error.status === 404)
            {
                alert('Account-id dont exist.');
            }
            if (error.status === 500)
            {
                  alert('Account-id dont exist.');
            }
        });
    }

    self.updateAccount = function()
	{
	    self.service.update(
                { id: self.sharedService.account.account_id },
                self.sharedService.account,
                function (successResponse) {
                    alert('Account updated successfully');
                    $location.path('/search');
                },
                function (error) {
                    if (error.status === 403) {
                        self.updateAccount();
                    }
                    if (error.status === 500)
                    {
                         alert('Account-id dont exist.');
                         $location.path('/search');
                    }
                    self.loadAccounts();
                }
            );
	}

	self.GetUserDetails = function(id)
	 {
	        var extraPath = id ? '' : null;
            self.service.get({ extraPath: extraPath, id: id }).$promise.then(function(response)
			{

                self.accountItem = response.content;
            }).catch(function(error){
                if(error.status === 403)
				{
				        alert("in get userdetails function");
                        $location.path('/login');
                }
            });
    }

	self.GetBalanceDetails = function(id)
	 {
            self.balanceService.get({ extraPath: 'account', accountId:  self.sharedService.loggedInUserAccountId}).$promise.then(function(response)
			{
                self.balanceItem = response.content;
            }).catch(function(error){
                if(error.status === 403)
				{
                        $location.path('/login');
                }
            });
    }

	self.GetTransactionDetails = function(id)
	 {
            self.transactionService.get({ extraPath: 'account', accountId:  id}).$promise.then(function(response)
            			{
                            self.userTransactions = response.content;
                        }).catch(function(error){
                            if(error.status === 403)
            				{
                                    $location.path('/login');
                            }
                        });
    }

}


angular.module("bank-ui").controller('AccountController', ['$location', '$routeParams', 'AccountService', 'BalanceService', 'TransactionService', 'SharedService', AccountController]);

    }());