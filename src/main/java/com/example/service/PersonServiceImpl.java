package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Person;
import com.example.model.Person_;

@Service
public class PersonServiceImpl implements PersonService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addPerson(Person person) {
		em.persist( person );
	}
	
	@Transactional
	public List<Person> listPeople() {
		CriteriaQuery<Person> c = em.getCriteriaBuilder().createQuery( Person.class );
		c.from( Person.class );
		return em.createQuery( c ).getResultList();
	}

	@Transactional
	public void removePerson( Integer id ) {
		Person person = em.find( Person.class, id );
		if( null != person ) {
			em.remove( person );
		}
	}

	@Transactional
	public Person retrieve( Integer id ) {
		try{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		Root<Person> rootPerson = cq.from(Person.class);
		Predicate idEqual = cb.equal(rootPerson.get(Person_.id), id);
		cq.where(idEqual);
		TypedQuery<Person> query = em.createQuery(cq);
		Person person = query.getSingleResult();
		return person;
		}catch(NoResultException e){
			Person person = em.find( Person.class, id );
			return person;
		}
	}

	@Transactional
	public void update(Person person) {
		em.merge( person );
	}

	public Person retrieve(String username) {
		try{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Person> cq = cb.createQuery(Person.class);
			Root<Person> rootPerson = cq.from(Person.class);
			Predicate usernameEqual = cb.equal(rootPerson.get(Person_.email), username);
			cq.where(usernameEqual);
			TypedQuery<Person> query = em.createQuery(cq);
			return query.getSingleResult();
		}catch(NoResultException exception){
			return null;
		}
	}

}
