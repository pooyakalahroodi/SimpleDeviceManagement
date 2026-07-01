package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.mapper.SdmDepartmentMapper;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDepartmentRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentCoreService;

@Service
public class SdmDepartmentCoreServiceImpl implements SdmDepartmentCoreService {

	private final SdmDepartmentRepository sdmDepartmentRepository;
	private final SdmDepartmentMapper mapper;

	public SdmDepartmentCoreServiceImpl(SdmDepartmentRepository sdmDepartmentRepository, SdmDepartmentMapper mapper) {
		this.sdmDepartmentRepository = sdmDepartmentRepository;
		this.mapper = mapper;
	}

	@Override
	public List<SdmDepartmentTo> findAllDepartments() {
		return mapper.toToList(sdmDepartmentRepository.findAll());
	}

	@Override
	public SdmDepartmentTo saveDepartment(SdmDepartmentTo department) {
		SdmDepartmentEntity entity = mapper.toEntity(department);
		return mapper.toTo(sdmDepartmentRepository.save(entity));
	}

	@Override
	public boolean departmentExists(String departmentName) {
		return sdmDepartmentRepository.existsByNameIgnoreCase(departmentName);
	}

}
