package com.progiton.trainee.simple.devicemanagement.exceptions;

public class SdmEntityAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public SdmEntityAlreadyExistsException(String message) {
		super(message);
	}
}
