package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.DepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.services.DepartmentService;
import com.progiton.trainee.simple.devicemanagement.repositories.DepartmentRepository;

import org.springframework.stereotype.Service;


@Service
public class DepartmentServiceImpl implements DepartmentService{
	
    private final DepartmentRepository departmentRepository;


	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
		
    }

	@Override
	public List<DepartmentEntity> getAllDepartments() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DepartmentEntity getDepartmentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteDepartment(Long id) {
		// TODO Auto-generated method stub
		
	}



}