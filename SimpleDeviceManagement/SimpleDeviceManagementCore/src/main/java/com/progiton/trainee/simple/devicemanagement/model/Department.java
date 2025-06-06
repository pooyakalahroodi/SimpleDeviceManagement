package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDateTime;

public interface Department {
	String getName();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    

    // Optional: You can add user count or a DTO-friendly transformation
    default String getLabel() {
        return getName() + " (since " + getCreatedAt().toLocalDate() + ")";
    }
}
