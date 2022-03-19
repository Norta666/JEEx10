package edu.nbcc.model;

import java.util.ArrayList;
import java.util.List;


public class ErrorModel {

	private List<String> errors = new ArrayList<>();
	
	
	public ErrorModel() {
		
	}
	
	public ErrorModel(String error) {
		this.errors.add(error);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
