package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.model.Person;
import com.example.service.PersonService;
import com.mycompany.soap_web_service.GetPersonRequest;
import com.mycompany.soap_web_service.GetPersonResponse;

@Endpoint
public class PersonEndpoint {

	private static final String NAMESPACE_URI = "http://mycompany.com/soap-web-service";
	
	private PersonService personRepository;

	@Autowired
	public PersonEndpoint(PersonService personRepository) {
		this.personRepository = personRepository;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
	@ResponsePayload
	public GetPersonResponse getPerson(@RequestPayload GetPersonRequest request){
		GetPersonResponse response = new GetPersonResponse();
		Person person = personRepository.retrieve(request.getName());
		com.mycompany.soap_web_service.Person personResponse = new com.mycompany.soap_web_service.Person();
		personResponse.setName(person.getFirstName());
		personResponse.setLastname(person.getLastName());
		response.setPerson(personResponse);
		
		return response;
	}
}
