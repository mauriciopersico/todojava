(function(){

	'use strict'

	var todosService = function($http, $translate, apiBaseUrl, $state, messageBoxService) {

		var todosBaseUrl = apiBaseUrl + '/todo';

		this.retrieveAll = function() {
			return $http.get(todosBaseUrl + '/retrieve-all')
				.then(function(data){
					return data.data.todoList;
				})
		};

		this.save = function(todo) {
			var fd = new FormData();
        	fd.append('title', todo.title ? todo.title : '');
        	fd.append('duedate', todo.dueDate ? todo.dueDate : '');
        	fd.append('description', todo.description ? todo.description: '');
        	fd.append('done', todo.done ? todo.done: false);

        	$http.post(todosBaseUrl + '/create', fd, {
            	transformRequest: angular.identity,
            	headers: {'Content-Type': undefined}
    		})
        	.success(function(data, status, headers, config){
        		if(data.error){
        			messageBoxService.showError(data.error.cause);
        		}else{
        			messageBoxService.showSuccess($translate.instant('todo.success'))
        			$state.go('todos.list');
        		}
        	})
        	.error(function(data, status, headers, config){
				messageBoxService.showError($translate.instant('todo.error'));
        	});
		}

		this.getTodoById = function(todoId) {
			return $http.get(todosBaseUrl + '/get-todo-by-id/'+ todoId)
				.then(function(data){
					var parseDate = new Date(data.data.todo.dueDate);
					data.data.todo.dueDate = parseDate.toISOString().substring(0, 10);
					return data.data.todo;
				});
		}

		this.update = function(todo) {
			var fd = new FormData();
        	fd.append('title', todo.title ? todo.title : '');
        	fd.append('duedate', todo.dueDate ? todo.dueDate : '');
        	fd.append('description', todo.description ? todo.description: '');
        	fd.append('done', todo.done ? todo.done: false);
        	fd.append('todoId', todo.id);
        	fd.append('version', todo.version);

        	$http.post(todosBaseUrl + '/update', fd, {
            	transformRequest: angular.identity,
            	headers: {'Content-Type': undefined}
    		})
        	.success(function(data, status, headers, config){
        		if(data.error){
        			messageBoxService.showError(data.error.cause);
        		}else{
        			messageBoxService.showSuccess($translate.instant('user.success'))
        			$state.go('todos.list');
        		}
        	})
        	.error(function(data, status, headers, config){
				messageBoxService.showError($translate.instant('user.error'));
        	});
		}

		this.delete = function(todoId, callback){
        	$http.delete(todosBaseUrl + '/delete/' + todoId)
				.then(function(response){
					callback(null, response)
				}, function(err){
					callback(err, null)
				});
		}
		
	}

	angular.module('todos')
		.service('todosService', todosService)
})();