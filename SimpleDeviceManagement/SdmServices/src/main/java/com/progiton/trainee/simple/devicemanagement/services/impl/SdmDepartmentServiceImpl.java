package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentCoreService;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;

@Service
public class SdmDepartmentServiceImpl implements SdmDepartmentService {

	private final SdmDepartmentCoreService sdmDepartmentCoreService;

	public SdmDepartmentServiceImpl(SdmDepartmentCoreService sdmDepartmentCoreService) {
		this.sdmDepartmentCoreService = sdmDepartmentCoreService;
	}

	@Override
	public List<SdmDepartmentTo> findAllDepartments() {
		return sdmDepartmentCoreService.findAllDepartments();
	}

	@Override
	public SdmDepartmentTo saveDepartment(SdmDepartmentTo department) {
		return sdmDepartmentCoreService.saveDepartment(department);
	}

	@Override
	public boolean departmentExists(String departmentName) {
		return sdmDepartmentCoreService.departmentExists(departmentName);
	}

}