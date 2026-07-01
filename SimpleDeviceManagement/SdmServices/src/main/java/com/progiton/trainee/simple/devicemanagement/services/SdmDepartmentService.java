package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;

@Service
public class SdmDepartmentService {

	private final SdmDepartmentCoreService sdmDepartmentCoreService;

	public SdmDepartmentService(SdmDepartmentCoreService sdmDepartmentCoreService) {
		this.sdmDepartmentCoreService = sdmDepartmentCoreService;
	}

	public List<SdmDepartmentTo> findAllDepartments() {
		return sdmDepartmentCoreService.findAllDepartments();
	}

	public SdmDepartmentTo saveDepartment(SdmDepartmentTo department) {
		return sdmDepartmentCoreService.saveDepartment(department);
	}

	public boolean departmentExists(String departmentName) {
		return sdmDepartmentCoreService.departmentExists(departmentName);
	}

}