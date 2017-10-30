package com.example.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import com.example.model.Error;
import com.example.model.Todo;
import com.example.service.TodoService;

@Controller
@RequestMapping( "/todo" )
public class TodoController {

	private final static Logger LOGGER = Logger.getLogger(TodoController.class.getName());
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private TodoService todoService;
	
	@RequestMapping( "/retrieve-all" )
	public List<Todo> listTodo( Map<String, Object> map ) {
		LOGGER.info("Retrieving todos");
		return todoService.listTodo();
	}

	@RequestMapping( value = "get-todo-by-id/{todoId}" )
	public Todo getTodoById( @PathVariable( "todoId" ) Integer todoId ) {

		LOGGER.info("Todo retrieved: " 
				+"[" + " id: " + todoId + "]");
		return todoService.retrieve( todoId );
	}

	@RequestMapping( value = "/create", method = RequestMethod.POST )
	public String addTodo( @RequestParam( "title" ) String title, 
			@RequestParam( "duedate" ) String duedate,
			@RequestParam( "description" ) String description,
			@RequestParam( "done" ) Boolean done, 
			HttpServletRequest request) throws ValidationException, ParseException {

		PersonUser user = (PersonUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date dueDateParse = formatter.parse(duedate);
		Locale locale = localeResolver.resolveLocale(request);
		validateTodo(locale, title, duedate, description, done);
		Todo todo = new Todo(title, dueDateParse, description, done);
		todoService.addTodo(todo, user.getId());
		LOGGER.info("Todo created: " 
		+"[" + " title: " + title + "," 
		+ " dueDate: " + duedate + ","
		+ " description: " + description + ","
		+ " done: " + done + "]");
		return "redirect:/todo/";
	}
	
	@RequestMapping( value = "/update", method = RequestMethod.POST )
	public String updateTodo( @RequestParam( "title" ) String title, 
			@RequestParam( "duedate" ) String duedate,
			@RequestParam( "description" ) String description,
			@RequestParam( "done" ) Boolean done,
			@RequestParam( "todoId" ) Integer todoId, 
			@RequestParam( "version" ) Long version,
			HttpServletRequest request) throws ValidationException, ParseException {


		PersonUser user = (PersonUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date dueDateParse = formatter.parse(duedate);
	    Locale locale = localeResolver.resolveLocale(request);
		this.validateFieldsRequired(locale, title, duedate, description, done);
		Todo todo = new Todo(todoId, version, title, dueDateParse, description, done);
		todoService.update(todo, user.getId());
		LOGGER.info("Todo updated: " 
				+"[" + " title: " + title + "," 
				+ " dueDate: " + duedate + ","
				+ " description: " + description + ","
				+ " done: " + done + "]");
		return "redirect:/todo/";
	}

	@RequestMapping( value = "/delete/{todoId}", method = RequestMethod.DELETE )
	public String deleteTodo( @PathVariable( "todoId" ) Integer todoId ) {

		todoService.removeTodo(todoId);
		LOGGER.info("Todo deleted: " 
				+"[" + " id: " + todoId + "]");
		return "redirect:/todo/";
	}
	
	@ExceptionHandler(ValidationException.class)
	public Error handleValidationException(ValidationException exception){
		Error exceptionMessage = new Error(exception.getMessage());
		return exceptionMessage;
	}
	
	private void validateTodo(Locale locale, String title, String dueDate, String description, Boolean done) throws ValidationException{
		this.validateFieldsRequired(locale, title, dueDate, description, done);
	}
	
	private void validateFieldsRequired(Locale locale, String title, String dueDate, String description, Boolean done) throws ValidationException{
		this.validateFieldRequired(locale, title, "validation.todo.title");
		this.validateFieldRequired(locale, dueDate, "validation.todo.dueDate");
		this.validateFieldRequired(locale, description, "validation.todo.description");
		this.validateFieldRequired(locale, done, "validation.todo.done");
		this.validateDateRequired(locale, dueDate, "validation.todo.dueDateformat");
	}
	
	private void validateFieldRequired(Locale locale, String field, String messageKey) throws ValidationException{
		if(field == null || field.isEmpty()){
			String message = messages.getMessage(messageKey, new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		}
	}
	
	private void validateFieldRequired(Locale locale, Boolean field, String messageKey) throws ValidationException{
		if(field == null){
			String message = messages.getMessage(messageKey, new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		}
	}
	
	private void validateDateRequired(Locale locale, String field, String messageKey) throws ValidationException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			formatter.parse(field);
		} catch (ParseException e) {
			String message = messages.getMessage(messageKey, new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		}
	}
}