package com.blackbeard.teach.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorModel {
	private String	field;
	private String	errorMessage;

	public ValidationErrorModel(String field, String errorMessage) {
		this.field = field;
		this.errorMessage = errorMessage;
	}
}
