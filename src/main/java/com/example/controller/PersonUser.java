package com.example.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class PersonUser extends User {

	private static final long serialVersionUID = 6281439669197284294L;

	private Integer id;
	
	public PersonUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id) {
		super(username, password, authorities);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
