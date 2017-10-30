package com.example.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import com.example.model.Error;
import com.example.model.Person;
import com.example.service.PersonService;

@Controller
@RequestMapping( "/people" )
public class PersonController {

	@Autowired
	private MessageSource messages;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private PersonService personService;

	@RequestMapping( "/message" )
	public Error showMessage(  HttpServletRequest request) throws Exception{
		Locale resolve = localeResolver.resolveLocale(request);
		String message = messages.getMessage("util.pepe", new Object[]{}, "Required", resolve);
		if(message != null)
			throw new ValidationException(message);
		Error exception = new Error(message);
		return exception;
	}
	
	@RequestMapping( "/retrieve-all" )
	public List<Person> listPeople( Map<String, Object> map ) {

		return personService.listPeople();
	}

	@RequestMapping( value = "get-person-by-id/{personId}" )
	public Person getPersonById( @PathVariable( "personId" ) Integer personId ) {

		return personService.retrieve( personId );
	}

	@RequestMapping( value = "/create", method = RequestMethod.POST )
	public String addPerson( @RequestParam( "firstName" ) String firstName, 
			@RequestParam( "lastName" ) String lastName,
			@RequestParam( "password" ) String password,
			@RequestParam( "email" ) String email,
			@RequestParam( "rols" ) List<Integer> rols, HttpServletRequest request) throws ValidationException {

		Locale locale = localeResolver.resolveLocale(request);
		validateUser(locale, firstName, lastName, password, email, rols);
		Person person = new Person( firstName, lastName, password, email);
		personService.addPerson( person );

		return "redirect:/people/";
	}
	
	@RequestMapping( value = "/update", method = RequestMethod.POST )
	public String addPerson( @RequestParam( "firstName" ) String firstName,
			@RequestParam( "lastName" ) String lastName,
			@RequestParam( "password" ) String password,
			@RequestParam( "email" ) String email,
			@RequestParam( "rols" ) List<Integer> rols,
			@RequestParam( "personId" ) Integer personId, HttpServletRequest request) throws ValidationException {

		Locale locale = localeResolver.resolveLocale(request);
		this.validateFieldsRequired(locale, firstName, lastName, password, email, rols);
		Person person = personService.retrieve( personId );
		if( person == null ) {
			String message = messages.getMessage("validation.person.userNotExist", new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		} else {
			person.setFirstName( firstName );
			person.setLastName( lastName );
			person.setPassword(password);
			person.setEmail(email);
			personService.update( person);
		}

		return "redirect:/people/";
	}

	@RequestMapping( "/delete/{personId}" )
	public String deletePerson( @PathVariable( "personId" ) Integer personId ) {

		personService.removePerson( personId );

		return "redirect:/people/";
	}
	
	@ExceptionHandler(ValidationException.class)
	public Error handleValidationException(ValidationException exception){
		Error exceptionMessage = new Error(exception.getMessage());
		return exceptionMessage;
	}
	
	private void validateUser(Locale locale, String firstName, String lastName, String password, String email, List<Integer> rols) throws ValidationException{
		this.validateUserWithSameName(locale, firstName);
		this.validateFieldsRequired(locale, firstName, lastName, password, email, rols);
	}
	private void validateUserWithSameName(Locale locale, String username) throws ValidationException{
		Person person = personService.retrieve(username);
		if(person != null){
			String message = messages.getMessage("validation.person.usernamerepeated", new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		}
	}
	
	private void validateFieldsRequired(Locale locale, String firstName, String lastName, String password, String email, List<Integer> rols) throws ValidationException{
		this.validateFieldRequired(locale, firstName, "validation.person.firstName");
		this.validateFieldRequired(locale, lastName, "validation.person.lastName");
		this.validateFieldRequired(locale, password, "validation.person.password");
		this.validateFieldRequired(locale, email, "validation.person.email");
		this.validateFieldCollectionRequired(locale, rols, "validation.person.roles");
	}
	
	private void validateFieldRequired(Locale locale, String field, String messageKey) throws ValidationException{
		if(field == null || field.isEmpty()){
			String message = messages.getMessage(messageKey, new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		}
	}
	
	@SuppressWarnings("rawtypes") 
	private void validateFieldCollectionRequired(Locale locale, List list, String messageKey) throws ValidationException{
		if(list == null || (list != null && list.isEmpty())){
			String message = messages.getMessage(messageKey, new Object[]{}, "Required", locale);
			throw new ValidationException(message);
		}
	}
}
