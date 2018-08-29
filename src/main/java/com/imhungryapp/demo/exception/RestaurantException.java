package com.imhungryapp.demo.exception;

public class RestaurantException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public RestaurantException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public RestaurantException() {
		super();
	}
}