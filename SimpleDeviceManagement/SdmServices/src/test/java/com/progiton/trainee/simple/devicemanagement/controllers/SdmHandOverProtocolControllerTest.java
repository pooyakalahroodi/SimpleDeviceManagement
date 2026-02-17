package com.progiton.trainee.simple.devicemanagement.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;

class SdmHandOverProtocolControllerTest {

	@Mock
	private SdmHandOverProtocolService sdmHandOverProtocolService;

	@InjectMocks
	private SdmHandOverProtocolController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("createProtocol returns created protocol")
	void testCreateProtocol() {
		SdmHandOverProtocolTo input = new SdmHandOverProtocolTo();
		input.setDeviceSerialNumber("SN001");

		SdmHandOverProtocolTo saved = new SdmHandOverProtocolTo();
		saved.setDeviceSerialNumber("SN001");

		when(sdmHandOverProtocolService.saveHandOverProtocol(any(SdmHandOverProtocolTo.class))).thenReturn(saved);

		ResponseEntity<SdmHandOverProtocolTo> response = controller.createProtocol(input);

		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getDeviceSerialNumber()).isEqualTo("SN001");
	}

	@Test
	@DisplayName("getProtocolsByDeviceSerialNumber returns protocols")
	void testGetProtocolsByDeviceSerialNumber() {
		SdmHandOverProtocolTo protocol = new SdmHandOverProtocolTo();
		protocol.setDeviceSerialNumber("SN001");

		when(sdmHandOverProtocolService.findByDeviceSerialNumber("SN001")).thenReturn(List.of(protocol));

		ResponseEntity<List<SdmHandOverProtocolTo>> response = controller.getProtocolsByDeviceSerialNumber("SN001");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0).getDeviceSerialNumber()).isEqualTo("SN001");
	}

	@Test
	@DisplayName("getLatestProtocol returns latest protocol")
	void testGetLatestProtocol() {
		SdmHandOverProtocolTo p1 = new SdmHandOverProtocolTo();
		p1.setDeviceSerialNumber("SN001");
		p1.setHandoverDate(Instant.parse("2023-01-01T10:00:00Z")
);

		SdmHandOverProtocolTo p2 = new SdmHandOverProtocolTo();
		p2.setDeviceSerialNumber("SN001");
		p2.setHandoverDate(Instant.parse("2024-01-01T10:00:00Z"));

		when(sdmHandOverProtocolService.findByDeviceSerialNumber("SN001")).thenReturn(List.of(p1, p2));

		ResponseEntity<SdmHandOverProtocolTo> response = controller.getLatestProtocol("SN001");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getHandoverDate()).isEqualTo(p2.getHandoverDate());
	}

	@Test
	@DisplayName("confirmLatestUnconfirmed returns confirmed protocol")
	void testConfirmLatestUnconfirmed() {
		SdmHandOverProtocolTo confirmed = new SdmHandOverProtocolTo();
		confirmed.setDeviceSerialNumber("SN001");

		when(sdmHandOverProtocolService.confirmByDeviceSerialNumber("SN001")).thenReturn(confirmed);

		ResponseEntity<SdmHandOverProtocolTo> response = controller.confirmLatestUnconfirmed("SN001");

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getDeviceSerialNumber()).isEqualTo("SN001");
	}

	@Test
	@DisplayName("getProtocolsByReceiverUserId returns protocols")
	void testGetProtocolsByReceiverUserId() {
		UUID receiverUuid = UUID.randomUUID();
		UUID performerUuid = UUID.randomUUID();
		SdmHandOverProtocolTo protocol = new SdmHandOverProtocolTo();
		protocol.setReceiverUserId(receiverUuid);

		when(sdmHandOverProtocolService.findHandOverProtocolsByReceiverUserId(receiverUuid))
				.thenReturn(List.of(protocol));

		ResponseEntity<List<SdmHandOverProtocolTo>> response = controller.getProtocolsByReceiverUserId(receiverUuid);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0).getReceiverUserId()).isEqualTo(receiverUuid);
	}
}
