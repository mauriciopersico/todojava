package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.controller.PersonUser;
import com.example.model.Person;

public class PersonDetailService implements UserDetailsService {

	@Autowired
	private PersonService personService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Person person = personService.retrieve(username);
		if(person == null){
			throw new UsernameNotFoundException("User not found");
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		user = new PersonUser(person.getFirstName(), person.getPassword(), authorities, person.getId());
		return user;
	}

}
