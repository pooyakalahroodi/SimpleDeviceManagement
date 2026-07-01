package com.progiton.trainee.simple.devicemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Not Found
public class SdmEntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SdmEntityNotFoundException(String message) {
		super(message);
	}
}
