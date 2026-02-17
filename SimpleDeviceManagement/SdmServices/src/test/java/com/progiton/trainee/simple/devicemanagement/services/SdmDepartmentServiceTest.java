package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;

@ExtendWith(MockitoExtension.class)
class SdmDepartmentServiceTest {

	@Mock
	private SdmDepartmentCoreService sdmDepartmentCoreService;

	@InjectMocks
	private SdmDepartmentService sdmDepartmentService;

	@Test
	void findAllDepartments_DelegatesToCoreService() {
		List<SdmDepartmentTo> expected = Collections.singletonList(new SdmDepartmentTo("IT"));
		when(sdmDepartmentCoreService.findAllDepartments()).thenReturn(expected);

		List<SdmDepartmentTo> result = sdmDepartmentService.findAllDepartments();

		assertThat(result).isEqualTo(expected);
		verify(sdmDepartmentCoreService).findAllDepartments();
	}

	@Test
	void saveDepartment_DelegatesToCoreService() {
		SdmDepartmentTo department = new SdmDepartmentTo("IT");
		when(sdmDepartmentCoreService.saveDepartment(department)).thenReturn(department);

		SdmDepartmentTo result = sdmDepartmentService.saveDepartment(department);

		assertThat(result).isEqualTo(department);
		verify(sdmDepartmentCoreService).saveDepartment(department);
	}

	@Test
	void departmentExists_DelegatesToCoreService() {
		when(sdmDepartmentCoreService.departmentExists("IT")).thenReturn(true);

		boolean result = sdmDepartmentService.departmentExists("IT");

		assertThat(result).isTrue();
		verify(sdmDepartmentCoreService).departmentExists("IT");
	}
}
