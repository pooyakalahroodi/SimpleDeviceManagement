package com.progiton.trainee.simple.devicemanagement.model;


public interface SdmDepartment {
	String getName();

    // Optional: You can add user count or a DTO-friendly transformation
    // TODO (LR): Die MEthode hier ist nicht so gut besonders als default
    // du kannst bei bedarf die toString() überschreiben hat auch ein bessere efekt in Log
	//    default String getLabel() {
	//        return getName() + " (since " + getCreatedAt().toLocalDate() + ")";
	//    }
}
