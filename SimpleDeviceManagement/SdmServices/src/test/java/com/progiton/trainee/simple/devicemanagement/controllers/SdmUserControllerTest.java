package com.progiton.trainee.simple.devicemanagement.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;
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

class SdmUserControllerTest {

	@Mock
	private SdmUserService service;

	@InjectMocks
	private SdmUserController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("getAllUsers returns all users")
	void testGetAllUsers() {
		UUID userId = UUID.randomUUID();
		SdmUserTo user = new SdmUserTo();
		user.setUserId(userId);
		user.setEmailAddress("testuser@example.com");
		user.setName("Test");
		user.setSurname("User");

		when(service.findAllUsers()).thenReturn(List.of(user));

		ResponseEntity<List<SdmUserTo>> response = controller.getAllUsers();

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0).getEmailAddress()).isEqualTo("testuser@example.com");
	}

	@Test
	@DisplayName("createUser returns created user")
	void testCreateUser() {
		UUID userId = UUID.randomUUID();
		SdmUserTo input = new SdmUserTo();
		input.setUserId(userId);
		input.setEmailAddress("newuser@example.com");
		input.setName("New");
		input.setSurname("User");

		SdmUserTo saved = new SdmUserTo();
		saved.setUserId(userId);
		saved.setEmailAddress("newuser@example.com");
		saved.setName("New");
		saved.setSurname("User");

		when(service.createUser(any(SdmUserTo.class))).thenReturn(saved);

		ResponseEntity<SdmUserTo> response = controller.createUser(input);

		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getBody().getEmailAddress()).isEqualTo("newuser@example.com");
	}

	@Test
	@DisplayName("getUsersByDepartment returns users")
	void testGetUsersByDepartment() {
		UUID userId = UUID.randomUUID();
		SdmUserTo user = new SdmUserTo();
		user.setUserId(userId);
		user.setEmailAddress("deptuser@example.com");

		when(service.findUsersByDepartmentName("IT")).thenReturn(List.of(user));

		ResponseEntity<List<SdmUserTo>> response = controller.getUsersByDepartment("IT");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0).getEmailAddress()).isEqualTo("deptuser@example.com");
	}

	@Test
	@DisplayName("getUsersByUsername returns user")
	void testGetUsersByUsername() {
		UUID userId = UUID.randomUUID();
		SdmUserTo user = new SdmUserTo();
		user.setUserId(userId);
		user.setEmailAddress("john.smith@example.com");

		when(service.findUserByUserId(userId)).thenReturn(user);

		ResponseEntity<SdmUserTo> response = controller.getUsersByUserId(userId);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getEmailAddress()).isEqualTo("john.smith@example.com");
	}

	@Test
	@DisplayName("assignDeviceToUser returns device")
	void testAssignDeviceToUser() {
		UUID userId = UUID.randomUUID();
		SdmDeviceTo device = new SdmDeviceTo();
		device.setSerialNumber("SN1234");
		device.setType("ModelX");

		when(service.assignDeviceToUser(userId, "SN1234")).thenReturn(device);

		SdmDeviceTo result = controller.assignDeviceToUser(userId, "SN1234");

		verify(service).assignDeviceToUser(userId, "SN1234");
		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("SN1234");
		assertThat(result.getType()).isEqualTo("ModelX");
	}

	@Test
	@DisplayName("assignDepartment returns updated user")
	void testAssignDepartmentToUser() {
		UUID userId = UUID.randomUUID();
		SdmUserTo user = new SdmUserTo();
		user.setUserId(userId);
		user.setEmailAddress("john.smith@example.com");

		SdmDepartmentTo deptTo = new SdmDepartmentTo();
		deptTo.setName("IT");
		user.setDepartment(deptTo);

		when(service.assignDepartmentToUser(userId, "IT")).thenReturn(user);

		ResponseEntity<SdmUserTo> response = controller.assignDepartment(userId, "IT");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getEmailAddress()).isEqualTo("john.smith@example.com");
		assertThat(response.getBody().getDepartment().getName()).isEqualTo("IT");
	}
}
