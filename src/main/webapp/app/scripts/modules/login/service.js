(function(){

	'use strict'

	var loginService = function(apiBaseUrl, messageBoxService, $state, $rootScope, $http, $location, $translate) {

		var self = this;

		var loginBaseUrl = apiBaseUrl + '/login';

		/*self.token = function(callback){
			$http.get(loginBaseUrl + '/token').then(
					function(response) {
						callback(response.data.token)
					},
					function(){
						callback(response.data.token)
					}
			);
		}*/

		var setTeailEdition = function(user){
			for (var i = 0; i < user.rols.length; i++) {
				var userrol = user.rols[i];
				for(var j = 0; j < userrol.rols.length; j++){
					if(userrol.rols[j].name.toUpperCase() == 'create user'.toUpperCase()){
						$rootScope.createuser = true;
					}else if(userrol.rols[j].name.toUpperCase() == 'list user'.toUpperCase()){
						$rootScope.listuser = true;
					}else if(userrol.rols[j].name.toUpperCase() == 'create document'.toUpperCase()){
						$rootScope.createdocument = true;
					}else if(userrol.rols[j].name.toUpperCase() == 'list document'.toUpperCase()){
						$rootScope.listdocument = true;
					}else if(userrol.rols[j].name.toUpperCase() == 'create rol'.toUpperCase()){
						$rootScope.createrol = true;
					}else if(userrol.rols[j].name.toUpperCase() == 'list rol'.toUpperCase()){
						$rootScope.listrol = true;
					}else if(userrol.rols[j].name.toUpperCase() == 'list report'.toUpperCase()){
						$rootScope.listreport = true;
					}
				}
				
			}
		}
		
		var setTeailEditionToFalse = function(){
			$rootScope.createdocument = false;
			$rootScope.listdocument = false;
			$rootScope.createuser = false;
			$rootScope.listuser = false;
			$rootScope.createrol = false;
			$rootScope.listrol = false;
			$rootScope.listreport = false;
		}
		
		self.authenticate = function(credentials, callback) {
				
				var headers = credentials ? {'X-Requested-With': 'XMLHttpRequest',
						authorization : "Basic "
								+ btoa(credentials.username + ":"
										+ credentials.password)
				} : {};

				$http.get(loginBaseUrl + '/user', {
					headers : headers
				}).then(function(response) {
					if (response.data.principal && response.data.principal.name) {
						$rootScope.authenticated = true;
						$rootScope.user = response.data.principal.principal;
						//setTeailEdition(response.data.principal.principal);
						callback && callback();
						/*self.token(function(tokenResponse){
							if(tokenResponse){
								$rootScope.authenticated = true;
								setTeailEdition(response.data.principal.principal);
							}else{
								$rootScope.authenticated = false;
								setTeailEditionToFalse();
							}
							callback && callback();
						});*/
					} else {
						$rootScope.authenticated = false;
						$rootScope.user = undefined;
						//setTeailEditionToFalse();
						callback && callback();
					}
				}, function(err) {
					$rootScope.authenticated = false;
					$rootScope.user = undefined;
					setTeailEditionToFalse();
					callback && callback();
				});
		}
		
		self.login = function(credentials) {
			self.authenticate(credentials, function() {
				if ($rootScope.authenticated) {
					messageBoxService.showSuccess($translate.instant('login.success'))
					$state.transitionTo('todos.list');
					self.error = false;
					$rootScope.authenticated = true;
				} else {
					messageBoxService.showError($translate.instant('login.error'))
					$location.path("/login");
					self.error = true;
					$rootScope.authenticated = false;
				}
			})
		};

		self.logout = function(){
			$http.post(loginBaseUrl + '/logout', {}).finally(function() {
				setTeailEditionToFalse();
				$rootScope.authenticated = false;
				$rootScope.user = undefined;
				$location.path("/");
			});
		}

	}

	angular.module('login')
		.service('loginService', loginService)
})();