package com.example.service;

import java.util.List;

import com.example.model.Person;

public interface PersonService {

	public void addPerson( Person person);
	
	public List<Person> listPeople();

	public void removePerson( Integer id );

	public Person retrieve( Integer id );
	
	public Person retrieve( String username );

	public void update( Person person);
}
