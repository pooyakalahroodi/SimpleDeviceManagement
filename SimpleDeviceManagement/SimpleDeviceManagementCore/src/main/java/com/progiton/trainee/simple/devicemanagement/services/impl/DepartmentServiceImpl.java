package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.persistent.model.DepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.services.DepartmentService;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.DepartmentRepository;

import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public DepartmentEntity getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    @Override
    public DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) {
        return departmentRepository.save(departmentEntity);
    }

    @Override
    public DepartmentEntity updateDepartment(Long id, DepartmentEntity updatedEntity) {
        DepartmentEntity existing = getDepartmentById(id);
        existing.setName(updatedEntity.getName());
        return departmentRepository.save(existing);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
    }
}