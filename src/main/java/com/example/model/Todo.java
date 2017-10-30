package com.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Todo {

	@Id
	@GeneratedValue
	private Integer id;

	@Version
	private Long version;

	private String title;

	private Date dueDate;
	
	private String description;
	
	private Boolean done;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "OWNER_ID" )
	private Person owner;
	
	public Todo() {

	}

	public Todo( Integer id, Long version, String title, Date dueDate, String description, Boolean done) {
		this.id = id;
		this.version = version;
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
		this.done = done;
	}
	
	public Todo( String title, Date dueDate, String description, Boolean done) {
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
		this.done = done;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

}
