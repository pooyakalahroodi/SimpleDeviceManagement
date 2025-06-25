package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDepartmentRepository;

import org.springframework.stereotype.Service;

@Service
public class SdmDepartmentServiceImpl implements SdmDepartmentService {

    private final SdmDepartmentRepository sdmDepartmentRepository;

    public SdmDepartmentServiceImpl(SdmDepartmentRepository sdmDepartmentRepository) {
        this.sdmDepartmentRepository = sdmDepartmentRepository;
    }

    @Override
    public List<SdmDepartmentEntity> getAllDepartments() {
        return sdmDepartmentRepository.findAll();
    }

    @Override
    public SdmDepartmentEntity getDepartmentById(Long id) {
        return sdmDepartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    @Override
    public SdmDepartmentEntity saveDepartment(SdmDepartmentEntity sdmDepartmentEntity) {
        return sdmDepartmentRepository.save(sdmDepartmentEntity);
    }

    @Override
    public SdmDepartmentEntity updateDepartment(Long id, SdmDepartmentEntity updatedEntity) {
        SdmDepartmentEntity existing = getDepartmentById(id);
        existing.setName(updatedEntity.getName());
        return sdmDepartmentRepository.save(existing);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!sdmDepartmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        sdmDepartmentRepository.deleteById(id);
    }
	@Override
	public boolean departmentExists(String departmentName) {
	    return sdmDepartmentRepository.existsByNameIgnoreCase(departmentName);
	}
}