package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import java.util.List;

public interface SdmDepartmentService {
	List<SdmDepartmentEntity> getAllDepartments();
	SdmDepartmentEntity getDepartmentById(Long id);
	SdmDepartmentEntity saveDepartment(SdmDepartmentEntity sdmDepartmentEntity);
    void deleteDepartment(Long id);
	SdmDepartmentEntity updateDepartment(Long id, SdmDepartmentEntity entity);
	boolean departmentExists(String departmentName);
	
}