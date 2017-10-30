package com.example.service;

import java.util.List;

import com.example.model.Todo;

public interface TodoService {

	public void addTodo( Todo todo, Integer ownerId);

	public List<Todo> listTodo();
	
	public void removeTodo( Integer id );

	public Todo retrieve( Integer id );
	
	public Todo retrieve( String title );

	public void update( Todo todo, Integer ownerId );
}
