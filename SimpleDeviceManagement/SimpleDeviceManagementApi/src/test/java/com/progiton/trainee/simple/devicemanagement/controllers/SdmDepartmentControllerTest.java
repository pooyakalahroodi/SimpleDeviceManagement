package com.progiton.trainee.simple.devicemanagement.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;

class SdmDepartmentControllerTest {

	@Mock
	private SdmDepartmentService sdmDepartmentService;

	@InjectMocks
	private SdmDepartmentController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("getAllDepartments returns list of departments")
	void testGetAllDepartments() {
		SdmDepartmentTo itDept = new SdmDepartmentTo();
		itDept.setName("IT");

		SdmDepartmentTo hrDept = new SdmDepartmentTo();
		hrDept.setName("HR");

		when(sdmDepartmentService.findAllDepartments()).thenReturn(List.of(itDept, hrDept));

		List<SdmDepartmentTo> result = controller.getAllDepartments();

		assertThat(result).hasSize(2);
		assertThat(result).extracting(SdmDepartmentTo::getName).containsExactlyInAnyOrder("IT", "HR");
	}

	@Test
	@DisplayName("createDepartment returns created department with 201 status")
	void testCreateDepartment() {
		SdmDepartmentTo input = new SdmDepartmentTo();
		input.setName("Finance");

		SdmDepartmentTo saved = new SdmDepartmentTo();
		saved.setName("Finance");

		when(sdmDepartmentService.saveDepartment(any(SdmDepartmentTo.class))).thenReturn(saved);

		ResponseEntity<SdmDepartmentTo> response = controller.createDepartment(input);

		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Finance");
	}
}
