package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.mapper.SdmDepartmentMapper;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDepartmentRepository;
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmDepartmentCoreServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmDepartmentCoreServiceImplTest {

	@Mock
	private SdmDepartmentRepository departmentRepository;

	@Mock
	private SdmDepartmentMapper departmentMapper;

	@InjectMocks
	private SdmDepartmentCoreServiceImpl SdmDepartmentCoreServiceImpl; // class under test

	private SdmDepartmentEntity itDepartmentEntity;
	private SdmDepartmentEntity hrDepartmentEntity;
	private SdmDepartmentEntity financeDepartmentEntity;

	private SdmDepartmentTo itDepartmentTo;
	private SdmDepartmentTo hrDepartmentTo;
	private SdmDepartmentTo financeDepartmentTo;

	@BeforeEach
	void setUp() {
		// IT
		itDepartmentEntity = new SdmDepartmentEntity();
		itDepartmentEntity.setId(1L);
		itDepartmentEntity.setName("IT");

		itDepartmentTo = new SdmDepartmentTo();
		itDepartmentTo.setName("IT");

		// HR
		hrDepartmentEntity = new SdmDepartmentEntity();
		hrDepartmentEntity.setId(2L);
		hrDepartmentEntity.setName("HR");

		hrDepartmentTo = new SdmDepartmentTo();
		hrDepartmentTo.setName("HR");

		// Finance
		financeDepartmentEntity = new SdmDepartmentEntity();
		financeDepartmentEntity.setId(3L);
		financeDepartmentEntity.setName("Finance");

		financeDepartmentTo = new SdmDepartmentTo();
		financeDepartmentTo.setName("Finance");
	}

	@Test
	void findAllDepartments_ReturnsMultipleDepartments() {
		List<SdmDepartmentEntity> entities = Arrays.asList(itDepartmentEntity, hrDepartmentEntity, financeDepartmentEntity);
		List<SdmDepartmentTo> tos = Arrays.asList(itDepartmentTo, hrDepartmentTo, financeDepartmentTo);

		when(departmentRepository.findAll()).thenReturn(entities);
		when(departmentMapper.toToList(entities)).thenReturn(tos);

		List<SdmDepartmentTo> result = SdmDepartmentCoreServiceImpl.findAllDepartments();

		assertThat(result).isNotNull();
		assertThat(result).hasSize(3);
		assertThat(result).extracting(SdmDepartmentTo::getName).containsExactlyInAnyOrder("IT", "HR", "Finance");

		verify(departmentRepository).findAll();
		verify(departmentMapper).toToList(entities);
	}

	@Test
	void saveDepartment_ReturnsSavedTo() {
		when(departmentMapper.toEntity(itDepartmentTo)).thenReturn(itDepartmentEntity);
		when(departmentRepository.save(itDepartmentEntity)).thenReturn(itDepartmentEntity);
		when(departmentMapper.toTo(itDepartmentEntity)).thenReturn(itDepartmentTo);

		SdmDepartmentTo result = SdmDepartmentCoreServiceImpl.saveDepartment(itDepartmentTo);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("IT");

		verify(departmentMapper).toEntity(itDepartmentTo);
		verify(departmentRepository).save(itDepartmentEntity);
		verify(departmentMapper).toTo(itDepartmentEntity);
	}

	@Test
	void departmentExists_ReturnsTrue() {
		when(departmentRepository.existsByNameIgnoreCase("HR")).thenReturn(true);

		boolean exists = SdmDepartmentCoreServiceImpl.departmentExists("HR");

		assertThat(exists).isTrue();

		verify(departmentRepository).existsByNameIgnoreCase("HR");
	}

	@Test
	void departmentExists_ReturnsFalse() {
		when(departmentRepository.existsByNameIgnoreCase("NonExistingDept")).thenReturn(false);

		boolean exists = SdmDepartmentCoreServiceImpl.departmentExists("NonExistingDept");

		assertThat(exists).isFalse();

		verify(departmentRepository).existsByNameIgnoreCase("NonExistingDept");
	}

}
