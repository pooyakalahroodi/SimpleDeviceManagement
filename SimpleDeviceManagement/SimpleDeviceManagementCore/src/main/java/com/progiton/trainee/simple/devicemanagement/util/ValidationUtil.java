package com.progiton.trainee.simple.devicemanagement.util;

import com.progiton.trainee.simple.devicemanagement.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class ValidationUtil {

    /**
     * One smart method: handles null, empty strings, and empty lists
     */
    public static Object throwIfNullOrEmpty(Object value, String message) throws ApiException{
        if (value == null) {
            throw new ApiException(message, HttpStatus.NOT_FOUND);
        }

        if (value instanceof String str && str.trim().isEmpty()) {
            throw new ApiException(message, HttpStatus.BAD_REQUEST);
        }

        if (value instanceof List<?> list && list.isEmpty()) {
            throw new ApiException(message, HttpStatus.NOT_FOUND);
        }

        return value;
    }

    /**
     * Optional fallback for direct Optional<T> usage
     */
    public static <T> T throwIfEmpty(Optional<T> optional, String message) {
        if (optional == null || optional.isEmpty()) {
            throw new ApiException(message, HttpStatus.NOT_FOUND);
        }
        return optional.get();
    }

    /**
     * Boolean condition check
     */
    public static void throwIfFalse(boolean condition, String message) {
        if (!condition) {
            throw new ApiException(message, HttpStatus.BAD_REQUEST);
        }
    }
}