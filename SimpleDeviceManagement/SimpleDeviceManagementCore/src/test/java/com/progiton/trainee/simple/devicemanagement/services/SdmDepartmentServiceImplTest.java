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
	private SdmDepartmentServiceImpl departmentService;

	private SdmDepartmentEntity itEntity;
	private SdmDepartmentEntity hrEntity;
	private SdmDepartmentEntity financeEntity;

	private SdmDepartmentTo itDto;
	private SdmDepartmentTo hrDto;
	private SdmDepartmentTo financeDto;

	@BeforeEach
	void setUp() {
		// IT
		itEntity = new SdmDepartmentEntity();
		itEntity.setId(1L);
		itEntity.setName("IT");

		itDto = new SdmDepartmentTo();
		itDto.setName("IT");

		// HR
		hrEntity = new SdmDepartmentEntity();
		hrEntity.setId(2L);
		hrEntity.setName("HR");

		hrDto = new SdmDepartmentTo();
		hrDto.setName("HR");

		// Finance
		financeEntity = new SdmDepartmentEntity();
		financeEntity.setId(3L);
		financeEntity.setName("Finance");

		financeDto = new SdmDepartmentTo();
		financeDto.setName("Finance");
	}

	@Test
	void findAllDepartments_ReturnsMultipleDepartments() {
		List<SdmDepartmentEntity> entities = Arrays.asList(itEntity, hrEntity, financeEntity);
		List<SdmDepartmentTo> dtos = Arrays.asList(itDto, hrDto, financeDto);

		when(departmentRepository.findAll()).thenReturn(entities);
		when(departmentMapper.toToList(entities)).thenReturn(dtos);

		List<SdmDepartmentTo> result = departmentService.findAllDepartments();

		assertThat(result).isNotNull().hasSize(3).extracting(SdmDepartmentTo::getName).containsExactlyInAnyOrder("IT",
				"HR", "Finance");

		verify(departmentRepository).findAll();
		verify(departmentMapper).toToList(entities);
	}

	@Test
	void saveDepartment_ReturnsSavedDto() {
		when(departmentMapper.toEntity(itDto)).thenReturn(itEntity);
		when(departmentRepository.save(itEntity)).thenReturn(itEntity);
		when(departmentMapper.toTo(itEntity)).thenReturn(itDto);

		SdmDepartmentTo result = departmentService.saveDepartment(itDto);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("IT");

		verify(departmentMapper).toEntity(itDto);
		verify(departmentRepository).save(itEntity);
		verify(departmentMapper).toTo(itEntity);
	}

	@Test
	void departmentExists_ReturnsTrue() {
		when(departmentRepository.existsByNameIgnoreCase("HR")).thenReturn(true);

		boolean exists = departmentService.departmentExists("HR");

		assertThat(exists).isTrue();

		verify(departmentRepository).existsByNameIgnoreCase("HR");
	}

	@Test
	void departmentExists_ReturnsFalse() {
		when(departmentRepository.existsByNameIgnoreCase("NonExistingDept")).thenReturn(false);

		boolean exists = departmentService.departmentExists("NonExistingDept");

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

		SdmDepartmentTo result = departmentService.saveDepartment(nullNameDto);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isNull();

		verify(departmentMapper).toEntity(nullNameDto);
		verify(departmentRepository).save(savedEntity);
		verify(departmentMapper).toTo(savedEntity);
	}
}
