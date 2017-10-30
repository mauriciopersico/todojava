package com.example.controller;

import java.lang.reflect.Method;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

public class JsonModelAndViewResolver implements ModelAndViewResolver {

	@SuppressWarnings( "rawtypes" )
	public ModelAndView resolveModelAndView( Method handlerMethod,
			Class handlerType,
			Object returnValue,
			ExtendedModelMap implicitModel,
			NativeWebRequest webRequest ) {
		ModelAndView modelAndView = new ModelAndView( "jsonView" );
		modelAndView.addObject( "data", returnValue );
		return modelAndView;
	}

}