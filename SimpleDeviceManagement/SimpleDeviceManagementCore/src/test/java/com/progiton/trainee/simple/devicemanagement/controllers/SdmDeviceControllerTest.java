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

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;

class SdmDeviceControllerTest {

	@Mock
	private SdmDeviceService sdmDeviceService;

	@InjectMocks
	private SdmDeviceController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("getAllDevices returns list of devices")
	void testGetAllDevices() {
		SdmDeviceTo device = new SdmDeviceTo();
		device.setSerialNumber("SN123");
		device.setType("Laptop");

		when(sdmDeviceService.findAllDevices()).thenReturn(List.of(device));

		List<SdmDeviceTo> result = controller.getAllDevices();

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getSerialNumber()).isEqualTo("SN123");
	}

	@Test
	@DisplayName("saveDevice returns saved device")
	void testSaveDevice() {
		SdmDeviceTo input = new SdmDeviceTo();
		input.setSerialNumber("SN999");
		input.setType("Tablet");

		SdmDeviceTo saved = new SdmDeviceTo();
		saved.setSerialNumber("SN999");
		saved.setType("Tablet");

		when(sdmDeviceService.saveDevice(any(SdmDeviceTo.class))).thenReturn(saved);

		ResponseEntity<SdmDeviceTo> response = controller.saveDevice(input);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getSerialNumber()).isEqualTo("SN999");
	}

	@Test
	@DisplayName("updateDeviceStatus returns updated device")
	void testUpdateDeviceStatus() {
		SdmDeviceTo updated = new SdmDeviceTo();
		updated.setSerialNumber("SN777");
		updated.setType("Phone");
		updated.setStatus("Active");

		when(sdmDeviceService.updateDeviceStatus("SN777", "Active")).thenReturn(updated);

		ResponseEntity<SdmDeviceTo> response = controller.updateDeviceStatus("SN777", "Active");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getSerialNumber()).isEqualTo("SN777");
		assertThat(response.getBody().getStatus()).isEqualTo("Active");
	}
}
