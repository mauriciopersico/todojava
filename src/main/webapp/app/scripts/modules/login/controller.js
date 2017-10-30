(function(){

	'use strict'

	var loginController = function(loginService, $rootScope, $location) {
		var self = this;

		self.credentials = {};

		loginService.authenticate(undefined, function() {
			if ($rootScope.authenticated) {
				$rootScope.authenticated = true;
				$location.path("/");
			} else {
				//self.error = true;
				$rootScope.authenticated = false;
			}
		});

		
		self.login = function(){
			loginService.login(self.credentials);
		}


		self.logout = function() {
			loginService.logout();
		}

	};

	angular.module('login')
		.controller('loginController', loginController);

})();