package com.example.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.model.Error;
import com.example.model.ValidationException;

@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(ValidationException.class)
	public Error handleValidationException(ValidationException exception){
		Error exceptionMessage = new Error(exception.getMessage());
		return exceptionMessage;
	}
}
