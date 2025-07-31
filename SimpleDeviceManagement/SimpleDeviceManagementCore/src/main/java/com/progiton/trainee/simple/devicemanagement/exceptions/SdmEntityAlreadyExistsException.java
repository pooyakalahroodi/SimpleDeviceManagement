package com.progiton.trainee.simple.devicemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
public class SdmEntityAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public SdmEntityAlreadyExistsException(String message) {
		super(message);
	}

	public SdmEntityAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
