(function() {
    'use strict';



    function LoginService($resource) {
        return $resource('/bank/api/v1/users/:username', { username: '@username'});
    }
    angular.module('bank-ui').factory('LoginService', ['$resource', LoginService]);

	function LoginSharedService() {
        var uname = '';
        var password = '';
        return this;
    }
    angular.module('bank-ui').factory('LoginSharedService', [LoginSharedService]);



    function LoginController($http, $location, LoginService) {
        var self = this;
        self.user = {};
        self.account={};
        self.details = {};

        self.service = LoginService;

        self.init = function(){
                console.log('init login');


        }


        self.login = function() {
                 console.log('Login came. 1');

                 console.log('Login came.');
                 console.log('Login came aa.');
                 console.log('Login came ahbjka.');

           $http.post('/bank/login',
                'username=' + self.user.user_name + '&password=' + self.user.user_password, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }).then(self.loginSuccess)
                          .catch(function (error) {
                              if (error.status === -1) {
                                alert('Something weird happened. Please Login again !!!');
                              }
                              if (error.status === 403) {
                                  self.login();
                              }
                          });
        }

        self.loginSuccess = function(response)
        {
            console.log('Login successful in.');
            console.log('name: '+self.user.user_name);

            if (self.user.user_name == "admin")
		    {
                alert('Login successful.');
			    $location.path('/search');
             }
             else
		    {

		           var nameParam = self.user.user_name;
		            console.log('name: '+nameParam);
                    self.service.get({ username: nameParam }).$promise.then(function(response)
                    {
                        console.log("within function");
                        console.log(response);
                        console.log("within function before: "+self.details);
                        self.details = response;
                        console.log("name : "+self.details.user_name);
                        console.log(self.account);
                        console.log("account to be displayed : "+self.account);
                        var userId = self.details.user_id;
                        console.log("id to be shared : "+userId);
                        $location.path('/UserSearch/' + userId);
                    })
                    .catch(function(error)
                    {
                       console.error("Error fetching data: " + error);
                    });
                    console.log("calling search");

            }
        }

         self.logout = function() {
                    $http.post('/bank/logout').then(self.logoutSuccess)
                    .catch(function(error){
                        if(error.status === 403){
                            self.logout();
                        }
                        if(error.status === 405){
                             $location.path('/login');
                        }

                    })
                }

                self.logoutSuccess = function(response) {
                    $location.path('/login');
                }

        self.init();
    }

    angular.module('bank-ui').controller('LoginController', ['$http', '$location', 'LoginService',LoginController]);

}());

