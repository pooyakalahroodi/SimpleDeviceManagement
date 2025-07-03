package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;

@RestController
@RequestMapping("/api/handover-protocols")
public class SdmHandOverProtocolController {

	private final SdmHandOverProtocolService sdmHandOverProtocolService;

	public SdmHandOverProtocolController(SdmHandOverProtocolService service) {
		this.sdmHandOverProtocolService = service;
	}

	@PostMapping
	public ResponseEntity<SdmHandOverProtocolTo> createProtocol(@RequestBody SdmHandOverProtocolTo request) {
		// Save the entity using service layer
		SdmHandOverProtocolTo saved = sdmHandOverProtocolService.saveHandOverProtocol(request);

		// Return 201 Created with the new protocol
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	/**
	 * Get ALL handover protocols for a given device serial number
	 */
	@GetMapping("/device/{serialNumber}")
	public ResponseEntity<List<SdmHandOverProtocolTo>> getProtocolsByDeviceSerialNumber(
			@PathVariable String serialNumber) {
		List<SdmHandOverProtocolTo> handoverProtocols = sdmHandOverProtocolService
				.findByDeviceSerialNumber(serialNumber);
		return ResponseEntity.ok(handoverProtocols);
	}

	/**
	 * Get the LATEST handover protocol for a device
	 */
	@GetMapping("/device/{serialNumber}/latest")
	public ResponseEntity<SdmHandOverProtocolTo> getLatestProtocol(@PathVariable String serialNumber) {
		List<SdmHandOverProtocolTo> protocols = sdmHandOverProtocolService.findByDeviceSerialNumber(serialNumber);
		SdmHandOverProtocolTo latest = protocols.stream()
				.sorted((a, b) -> b.getHandoverDate().compareTo(a.getHandoverDate())).findFirst().orElseThrow(
						() -> new IllegalArgumentException("No protocols found for device serial: " + serialNumber));
		return ResponseEntity.ok(latest);
	}

	/**
	 * Confirm the latest unconfirmed handover protocol for a device
	 */
	@PutMapping("/device/{serialNumber}/confirm")
	public ResponseEntity<SdmHandOverProtocolTo> confirmLatestUnconfirmed(@PathVariable String serialNumber) {
		SdmHandOverProtocolTo confirmed = sdmHandOverProtocolService.confirmByDeviceSerialNumber(serialNumber);
		return ResponseEntity.ok(confirmed);
	}

	@GetMapping("/receiver/{username}")
	public ResponseEntity<List<SdmHandOverProtocolTo>> getProtocolsByReceiverUsername(@PathVariable String username) {
		List<SdmHandOverProtocolTo> handoverProtocols = sdmHandOverProtocolService
				.findHandOverProtocolsByReceiverUsername(username);
		return ResponseEntity.ok(handoverProtocols);
	}
}
