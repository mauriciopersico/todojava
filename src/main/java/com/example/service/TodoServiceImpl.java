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
import com.example.model.Todo;
import com.example.model.Todo_;

@Service
public class TodoServiceImpl implements TodoService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addTodo(Todo todo, Integer ownerId) {
		Person owner = em.find( Person.class, ownerId );
		todo.setOwner(owner);
		em.persist( todo );
	}

	public List<Todo> listTodo() {
		CriteriaQuery<Todo> c = em.getCriteriaBuilder().createQuery( Todo.class );
		c.from( Todo.class );
		return em.createQuery( c ).getResultList();
	}

	@Transactional
	public void removeTodo(Integer id) {
		Todo todo = em.find( Todo.class, id );
		if( todo != null ) {
			em.remove( todo );
		}
	}

	public Todo retrieve(Integer id) {
		Todo todo = em.find( Todo.class, id );
		return todo;
	}

	public Todo retrieve(String title) {
		try{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Todo> cq = cb.createQuery(Todo.class);
			Root<Todo> rootTodo = cq.from(Todo.class);
			Predicate titleEqual = cb.equal(rootTodo.get(Todo_.title), title);
			cq.where(titleEqual);
			TypedQuery<Todo> query = em.createQuery(cq);
			return query.getSingleResult();
		}catch(NoResultException exception){
			return null;
		}
	}

	@Transactional
	public void update(Todo todo, Integer ownerId) {
		Person owner = em.find( Person.class, ownerId );
		todo.setOwner(owner);
		em.merge( todo );
	}

}
