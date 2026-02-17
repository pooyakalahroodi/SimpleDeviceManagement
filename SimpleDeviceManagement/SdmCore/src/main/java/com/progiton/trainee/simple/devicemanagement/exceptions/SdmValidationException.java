// SdmCore/exceptions/SdmValidationException.java
package com.progiton.trainee.simple.devicemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST) // 400
public class SdmValidationException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public SdmValidationException(String message) {
        super(message);
    }

    public SdmValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
