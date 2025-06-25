package com.progiton.trainee.simple.devicemanagement.exceptions;

import java.time.Instant;


public class ErrorResponse {
    private String code;        // Custom error code like USER_NOT_FOUND
    private String message;     // Detailed message
    private String path;        // Request path
    private int status;         // HTTP status code (e.g. 404)
    private Instant timestamp;

    
    public ErrorResponse() {
        this.timestamp = Instant.now();
    }
    
    public ErrorResponse(String code, String message, String path, int status) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = Instant.now();
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    
}
