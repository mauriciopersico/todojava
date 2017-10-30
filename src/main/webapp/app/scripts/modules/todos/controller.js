(function(){

	'use strict'

	var todosListController = function(todosList, auxFunctions, todosService, loginService, $state, $rootScope, $location, $translate, messageBoxService) {
		var list = this;

		//filters
		
		list.filters = {
			description: null
		}

		//Filter function
		list.categoriesFilterFn = function(category) {
		    if(list.filters.title && category.title && (category.title.toLowerCase().indexOf(list.filters.title.toLowerCase()) == -1) ) {
		        return false;
		    };

		    return true;
		};

		list.delete = function(todoId){
			list.deleteProcess = true
			todosService.delete(todoId, function(err, response){
				if(err){
					messageBoxService.showError($translate.instant('todo.error') + ': ' + err.data);
				}else{
					messageBoxService.showInfo($translate.instant('todo.successdelete'));
					$state.go('todos.list', {}, {reload: true});
				}
				list.deleteProcess = false
			})
		};

		//Initialization Control
		function init() {
			list.todos = todosList;
			loginService.authenticate(undefined, function() {
				if ($rootScope.authenticated) {
					$rootScope.authenticated = true;
				} else {
					$rootScope.authenticated = false;
				}
			});
		};

		init();
	};

	var todoEditorController = function(todo, todosService, loginService, $rootScope, $scope, $location, auxFunctions) {
		
		loginService.authenticate(undefined, function() {
			if ($rootScope.authenticated) {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
		});

		var editor = this;
		editor.todo = todo;
		
		editor.save = function(todo) {
			todosService.save(todo);
		}

		editor.update = function(todo) {
			todosService.update(todo);
		}
		

	};

	var dashboardController = function($http, apiBaseUrl, loginService){
		var dashboard = this;

		loginService.authenticate();
		
	};
	
	angular.module('todos')
		.controller('todosListController', todosListController)
		.controller('todoCreationCtrl', todoEditorController)
		.controller('todoEditorCtrl', todoEditorController)
		.controller('dashboardController', dashboardController)
})();