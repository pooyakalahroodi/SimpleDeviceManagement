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
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;

class SdmUserControllerTest {

	@Mock
	private SdmUserService sdmUserService;

	@InjectMocks
	private SdmUserController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("getAllUsers returns all users")
	void testGetAllUsers() {
		SdmUserTo user = new SdmUserTo();
		user.setUsername("testuser");
		user.setName("Test");
		user.setSurname("User");

		when(sdmUserService.findAllUsers()).thenReturn(List.of(user));

		ResponseEntity<List<SdmUserTo>> response = controller.getAllUsers();

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0).getUsername()).isEqualTo("testuser");
	}

	@Test
	@DisplayName("createUser returns created user")
	void testCreateUser() {
		SdmUserTo input = new SdmUserTo();
		input.setUsername("newuser");
		input.setName("New");
		input.setSurname("User");

		SdmUserTo saved = new SdmUserTo();
		saved.setUsername("newuser");
		saved.setName("New");
		saved.setSurname("User");

		when(sdmUserService.createUser(any(SdmUserTo.class))).thenReturn(saved);

		ResponseEntity<SdmUserTo> response = controller.createUser(input);

		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getBody().getUsername()).isEqualTo("newuser");
	}

	@Test
	@DisplayName("getUsersByDepartment returns users")
	void testGetUsersByDepartment() {
		SdmUserTo user = new SdmUserTo();
		user.setUsername("deptuser");

		when(sdmUserService.findUsersByDepartmentName("IT")).thenReturn(List.of(user));

		ResponseEntity<List<SdmUserTo>> response = controller.getUsersByDepartment("IT");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0).getUsername()).isEqualTo("deptuser");
	}

	@Test
	@DisplayName("getUsersByUsername returns user")
	void testGetUsersByUsername() {
		SdmUserTo user = new SdmUserTo();
		user.setUsername("johnsmith");

		when(sdmUserService.findUserByUsername("johnsmith")).thenReturn(user);

		ResponseEntity<SdmUserTo> response = controller.getUsersByUsername("johnsmith");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getUsername()).isEqualTo("johnsmith");
	}

	@Test
	@DisplayName("assignDeviceToUser returns device")
	void testAssignDeviceToUser() {
		SdmDeviceTo device = new SdmDeviceTo();
		device.setSerialNumber("SN1234");
		device.setType("ModelX");

		when(sdmUserService.assignDeviceToUser("johnsmith", "SN1234")).thenReturn(device);

		SdmDeviceTo result = controller.assignDeviceToUser("johnsmith", "SN1234");

		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("SN1234");
		assertThat(result.getType()).isEqualTo("ModelX");
	}

	@Test
	@DisplayName("assignDepartment returns updated user")
	void testAssignDepartmentToUser() {
		SdmUserTo user = new SdmUserTo();
		user.setUsername("johnsmith");

		SdmDepartmentTo deptTo = new SdmDepartmentTo();
		deptTo.setName("IT");
		user.setDepartment(deptTo);

		when(sdmUserService.assignDepartmentToUser("johnsmith", "IT")).thenReturn(user);

		ResponseEntity<SdmUserTo> response = controller.assignDepartment("johnsmith", "IT");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getUsername()).isEqualTo("johnsmith");
		assertThat(response.getBody().getDepartment().getName()).isEqualTo("IT");
	}
}
