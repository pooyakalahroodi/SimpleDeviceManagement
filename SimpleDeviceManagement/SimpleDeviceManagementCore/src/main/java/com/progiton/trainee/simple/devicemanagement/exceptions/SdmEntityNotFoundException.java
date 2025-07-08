package com.progiton.trainee.simple.devicemanagement.exceptions;

public class SdmEntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SdmEntityNotFoundException(String message) {
		super(message);
	}
}
