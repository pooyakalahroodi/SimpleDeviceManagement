package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.DepartmentEntity;
import java.util.List;

public interface DepartmentService {
	List<DepartmentEntity> getAllDepartments();
	DepartmentEntity getDepartmentById(Long id);
	DepartmentEntity saveDepartment(DepartmentEntity departmentEntity);
    void deleteDepartment(Long id);
}