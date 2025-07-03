package com.progiton.trainee.simple.devicemanagement.model.to;


import com.progiton.trainee.simple.devicemanagement.model.SdmDepartment;

public class SdmDepartmentTo implements SdmDepartment {

	private String name;

    // Constructors
    public SdmDepartmentTo() {
        super();
    }

    public SdmDepartmentTo(final String name) {
        this.name = name;
    }

    // Getters & Setters
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
