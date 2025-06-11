package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.LocalDateTime;

import com.progiton.trainee.simple.devicemanagement.model.Department;

public class DepartmentTo extends SdmBaseTO implements Department {

	private String name;

    // Constructors
    public DepartmentTo() {
        super();
    }

    public DepartmentTo(final String name, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
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
