(function(){


'use strict';

/**
 * @ngdoc overview
 * @name cmspanelApp
 * @description
 * # cmspanelApp
 *
 * Main module of the application.
 */
angular
  .module('cmspanelApp', [
    'ngAnimate',
    'ui.router',
    'ceibo.ui',
    'ceibo.d3',
    'mgcrea.ngStrap',
    'smart-table',
    'fileReaderModule',
    'utils',
    'login',
    'todos',
    'angular-loading-bar',
    'pascalprecht.translate',
    'tmh.dynamicLocale'
    ])
  .constant('apiBaseUrl', location.origin + location.pathname + 'api')
  .controller('navBarCtrl', function(loginService){
    var navBar = this;

    navBar.username = undefined;
    
    navBar.logout = function(){
    	loginService.logout();
    };
  })
  .controller('latBarCtrl', function(loginService){
    var latBar = this;

    latBar.username = undefined;
    
    latBar.logout = function(){
    	loginService.logout();
    };
  })
  .config(['$translateProvider', '$stateProvider','$urlRouterProvider', '$httpProvider', function ($translateProvider, $stateProvider, $urlRouterProvider, $httpProvider) {

  $urlRouterProvider.otherwise('/todos/list');
  
  $stateProvider

    /*** Dashboard ***/

    .state('dashboard', {
      url: '/dashboard',
      abstract : true,
      template: '<ui-view/>'
    })

    .state('dashboard.main', {
      url: '/',
      templateUrl: 'app/views/dashboard.html',
      controller: 'dashboardController as dashboard',
    })
    
    /*** LOGIN ***/
    .state('login', {
      url: '/login',
      templateUrl: 'app/views/login/login.html',
      controller: 'loginController as login',
    })

    /*** TODOs ***/

    .state('todos', {
      url: '/todos',
      abstract : true,
      templateUrl: 'app/views/todos/todos.html'
      //template: '<ui-view/>'
    })

    .state('todos.list', {
      url: '/list',
      templateUrl: 'app/views/todos/list.html',
      controller: 'todosListController as list',
      resolve : {
        todosList : function(todosService) {
          return todosService.retrieveAll()
        }
      }
    })

    .state('todos.editor', {
      url: '/edit/:todoId',
      templateUrl: 'app/views/todos/todo-editor.html',
      controller: 'todoEditorCtrl as editor',
      resolve : {
        todo : function(todosService, $stateParams) {
          var todoId = $stateParams.todoId;
          return todosService.getTodoById(todoId);
        }
      }
    })

    .state('todos.create', {
      url: '/create',
      templateUrl: 'app/views/todos/todo-editor.html',
      controller: 'todoCreationCtrl as editor',
      resolve: {
        todo : function() {
          return {}
        }
      }
    })

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';    

//  	  $translateProvider.useSanitizeValueStrategy('sanitize');
	  $translateProvider.translations("es-es", {
			  "index": {
				  "login": "Iniciar sesión",
				  "logout": "Cerrar sesión"
			  },
			  "todo": {
				  "title": "TODOs",
				  "list": "Lista",
				  "buttoncreate": "Crear nuevo item to-do",
				  "listtitle": "My lista de to-do",
				  "titlef":"Titulo",
				  "description":"Descripción",
				  "duedate":"Fecha de Vencimiento",
				  "done":"&nbsp;Hecho",
				  "create": "Crear",
				  "titleedit": "Editar Tarea",
				  "titlecreate": "Crear Tarea",
				  "save": "Guardar",
				  "savechange": "Cancelar",
				  "error": "Ha ocurrido un error",
				  "success": "Éxito al guardar el item",
				  "successdelete": "Exito borrando el itemm"
			  },
			  "login": {
				"username": "Nombre de usuario",
				"password": "Contraseña",
				"submit": "Iniciar Sesión",
				"success": "Inicio de sesión exitoso",
				"error": "Error al iniciar la sesión"
			  },
			  "dashboard": {
				  "title": "Bienvenido",
				  "unauthenticated": "Inicie la sesión para ver el contenido."
			  },
			  "user": {
				  "title": "Usuarios",
				  "titleedit": "Editar usuario",
				  "titlecreate": "Crear usuario",
				  "list": "Lista",
				  "create": "Crear",
				  "unauthenticated": "Inicie la sesión para ver el contenido.",
				  "firstname": "Nombre",
				  "lastname": "Apellido",
				  "password": "Contraseña",
				  "email": "Correo electrónico",
				  "addrol": "Agregar Rol",
				  "save": "Guardar",
				  "savechange": "Guardar Cambios",
				  "cancel": "Cancelar",
				  "adduser": "Agregar usuario",
				  "actions": "Acciones",
				  "success": "Éxito al guardar el usuario"
			  }
	  });
	  
	  $translateProvider.translations("en-us", {
		  "index": {
			  "login": "Login",
			  "logout": "Logout"
		  },
		  "todo": {
			  "title": "TODOs",
			  "list": "List",
			  "buttoncreate": "Create new to-do item",
			  "listtitle": "My to-do list",
			  "titlef":"Title",
			  "description":"Description",
			  "duedate":"Due Date",
			  "done":"&nbsp;Done",
			  "create": "Create",
			  "titleedit": "Edit Task",
			  "titlecreate": "Create Task",
			  "save": "Save",
			  "savechange": "Save",
			  "error": "An error has ocurred",
			  "success": "Success saving the item",
			  "successdelete": "Success deleting the item"
		  },
		  "login": {
				"username": "Username",
				"password": "Password",
				"submit": "Log In",
				"success": "Login succeeded",
				"error": "Login failed"
		  },
		  "dashboard": {
			  "title": "Welcome",
			  "unauthenticated": "Login to see your content."
		  },
		  "user": {
			  "title": "Users",
			  "titleedit": "Edit User",
			  "titlecreate": "Create User",
			  "list": "List",
			  "create": "Create",
			  "unauthenticated": "Login to see your content.",
			  "firstname": "First Name",
			  "lastname": "Last Name",
			  "password": "Password",
			   "email": "Email",
 			   "addrol": "Add Rol",
			   "addaction": "Add accion",
			   "adduser": "Add user",
			   "actions": "Actions",
			   "success": "Success saving the user",
			   "cancel": "Cancel",
			   "save": "Save",
			   "savechange": "Save Changes"
		  }
	  });

  }])
  
  .run(["$window", "$translate", function($window, $translate){
	  var language = ($window.navigator.userLanguage || $window.navigator.language).indexOf("en") == 0 ? "en-us" : "es-es";
	  $translate.use(language);
  }])

})()