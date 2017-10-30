package com.example.controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mycompany.soap_web_service.Person;

@Component
public class PersonRepository {
	private static final List<Person> persons = new ArrayList<Person>();

	@PostConstruct
	public void initData() {
		Person spain = new Person();
		spain.setName("Spain");
		spain.setLastname("Madrid");

		persons.add(spain);

		Person poland = new Person();
		poland.setName("Poland");
		poland.setLastname("Warsaw");

		persons.add(poland);

		Person uk = new Person();
		uk.setName("United Kingdom");
		uk.setLastname("London");

		persons.add(uk);
	}

	public Person findPerson(String name) {
		Assert.notNull(name);

		Person result = null;

		for (Person person : persons) {
			if (name.equals(person.getName())) {
				result = person;
			}
		}
		
		return result;
	}
}