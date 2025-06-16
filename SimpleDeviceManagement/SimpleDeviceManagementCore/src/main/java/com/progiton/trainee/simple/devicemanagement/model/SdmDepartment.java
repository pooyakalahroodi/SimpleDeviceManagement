package com.progiton.trainee.simple.devicemanagement.model;


public interface SdmDepartment extends Auditable {
	String getName();
	
    default String getLabel() {
        return getName() + " (since " + getCreatedAt() + ")";
    }


}
