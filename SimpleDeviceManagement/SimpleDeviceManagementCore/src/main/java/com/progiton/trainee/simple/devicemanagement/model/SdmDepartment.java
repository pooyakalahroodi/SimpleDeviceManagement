package com.progiton.trainee.simple.devicemanagement.model;


import java.util.List;

public interface SdmDepartment extends Auditable {
	String getName();
	
    default String getLabel() {
        return getName() + " (since " + getCreatedAt() + ")";
    }

}
