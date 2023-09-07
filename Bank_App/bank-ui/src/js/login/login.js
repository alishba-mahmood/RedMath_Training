(function() {
    'use strict';



function LoginService($resource) {
        return $resource('bank/api/v1/accounts/:extraPath/:id', { extraPath: '@extraPath', id: '@id' });
    }
    angular.module('bank-ui').factory('LoginService', ['$resource', LoginService]);

function LoginSharedService() {
        var uname = '';
        var password = '';

        return this;
    }
    angular.module('bank-ui').factory('LoginSharedService', [LoginSharedService]);



    function LoginController($http, $location) {
        var self = this;
        self.user = {};


        self.init = function(){
                console.log('init login');

        }

        self.login = function() {
                 console.log('Login came.');
                 console.log('Login came aa.');

           $http.post('/bank/login',
                'username=' + self.user.user_name + '&password=' + self.user.user_password, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }).then(self.loginSuccess)
                          .catch(function (error) {
                              if (error.status === -1) {
                                alert('Login unsuccessful. Please check your credentials.');
                              }
                              if (error.status === 403) {
                                  self.login();
                              }
                          });
        }

        self.loginSuccess = function(response) {
        if (self.user.user_name == "admin") {
                                console.log('Login successful. Please check your credentials.');

            $location.path('/search');
                    }
                    else {
                        self.LoginAccountService.get({ extraPath: 'findByUserName', uname: self.user.user_name }).$promise.then(function(response) {
                        self.userAccountId = response.content.id;
                        $location.path('/accounts/' + self.userAccountId);
                        });
                   }
        }

         self.logout = function() {
                    $http.post('/bank/logout').then(self.logoutSuccess)
                    .catch(function(error){
                        if(error.status === 403){
                            self.logout();              //
                        }
                    })
                }

                self.logoutSuccess = function(response) {
                    $location.path('/login');
                }

        self.init();
    }

    angular.module('bank-ui').controller('LoginController', ['$http', '$location', LoginController]);

}());

