package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDateTime;

public interface Department {
	String getName();

    // Optional: You can add user count or a DTO-friendly transformation
    // TODO (LR): Die MEthode hier ist nicht so gut besonders als default
    // du kannst bei bedarf die toString() überschreiben hat auch ein bessere efekt in Log
//    default String getLabel() {
//        return getName() + " (since " + getCreatedAt().toLocalDate() + ")";
//    }
}
