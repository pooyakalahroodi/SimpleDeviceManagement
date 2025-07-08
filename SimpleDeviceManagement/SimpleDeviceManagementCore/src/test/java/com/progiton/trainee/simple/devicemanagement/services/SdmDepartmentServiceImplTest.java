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
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmDepartmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmDepartmentServiceImplTest {

	@Mock
	private SdmDepartmentRepository departmentRepository;

	@Mock
	private SdmDepartmentMapper departmentMapper;

	@InjectMocks
	private SdmDepartmentServiceImpl cut; // class under test

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
		List<SdmDepartmentTo> dtos = Arrays.asList(itDepartmentTo, hrDepartmentTo, financeDepartmentTo);

		when(departmentRepository.findAll()).thenReturn(entities);
		when(departmentMapper.toToList(entities)).thenReturn(dtos);

		List<SdmDepartmentTo> result = cut.findAllDepartments();

		assertThat(result).isNotNull();
		assertThat(result).hasSize(3);
		assertThat(result).extracting(SdmDepartmentTo::getName).containsExactlyInAnyOrder("IT", "HR", "Finance");

		verify(departmentRepository).findAll();
		verify(departmentMapper).toToList(entities);
	}

	@Test
	void saveDepartment_ReturnsSavedDto() {
		when(departmentMapper.toEntity(itDepartmentTo)).thenReturn(itDepartmentEntity);
		when(departmentRepository.save(itDepartmentEntity)).thenReturn(itDepartmentEntity);
		when(departmentMapper.toTo(itDepartmentEntity)).thenReturn(itDepartmentTo);

		SdmDepartmentTo result = cut.saveDepartment(itDepartmentTo);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("IT");

		verify(departmentMapper).toEntity(itDepartmentTo);
		verify(departmentRepository).save(itDepartmentEntity);
		verify(departmentMapper).toTo(itDepartmentEntity);
	}

	@Test
	void departmentExists_ReturnsTrue() {
		when(departmentRepository.existsByNameIgnoreCase("HR")).thenReturn(true);

		boolean exists = cut.departmentExists("HR");

		assertThat(exists).isTrue();

		verify(departmentRepository).existsByNameIgnoreCase("HR");
	}

	@Test
	void departmentExists_ReturnsFalse() {
		when(departmentRepository.existsByNameIgnoreCase("NonExistingDept")).thenReturn(false);

		boolean exists = cut.departmentExists("NonExistingDept");

		assertThat(exists).isFalse();

		verify(departmentRepository).existsByNameIgnoreCase("NonExistingDept");
	}

	@Test
	void saveDepartment_WithNullName_ReturnsDto() {
		SdmDepartmentTo nullNameDto = new SdmDepartmentTo();
		nullNameDto.setName(null);

		SdmDepartmentEntity savedEntity = new SdmDepartmentEntity();
		savedEntity.setId(99L);
		savedEntity.setName(null);

		when(departmentMapper.toEntity(nullNameDto)).thenReturn(savedEntity);
		when(departmentRepository.save(savedEntity)).thenReturn(savedEntity);
		when(departmentMapper.toTo(savedEntity)).thenReturn(nullNameDto);

		SdmDepartmentTo result = cut.saveDepartment(nullNameDto);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isNull();

		verify(departmentMapper).toEntity(nullNameDto);
		verify(departmentRepository).save(savedEntity);
		verify(departmentMapper).toTo(savedEntity);
	}
}
