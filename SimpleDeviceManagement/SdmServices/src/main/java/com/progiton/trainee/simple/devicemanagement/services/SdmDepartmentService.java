package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;

public interface SdmDepartmentService {
	List<SdmDepartmentTo> findAllDepartments();

	SdmDepartmentTo saveDepartment(SdmDepartmentTo department);

	boolean departmentExists(String departmentName);

}