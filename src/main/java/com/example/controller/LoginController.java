package com.example.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( "/login" )
public class LoginController {

	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
	  return user;
	}
	
	@RequestMapping("/token")
	@ResponseBody
	public Map<String,String> token(HttpSession session) {
		return Collections.singletonMap("token", session.getId());
	}
}
