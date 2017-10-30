package com.example.model;

public class Error {
	
	private String cause;

	public Error(String cause) {
		super();
		this.cause = cause;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
}
