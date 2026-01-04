package com.progiton.trainee.simple.devicemanagement.model.to;

import com.progiton.trainee.simple.devicemanagement.model.SdmDepartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SdmDepartmentTo implements SdmDepartment {

	@NotBlank // testable annotations
	@Size(max = 50)
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
