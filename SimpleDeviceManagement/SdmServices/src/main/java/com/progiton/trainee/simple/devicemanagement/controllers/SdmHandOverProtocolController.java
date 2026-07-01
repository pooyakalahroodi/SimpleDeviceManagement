package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmErrorResponse;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/api/handover-protocols")
public class SdmHandOverProtocolController {

	private final SdmHandOverProtocolService sdmHandOverProtocolService;

	public SdmHandOverProtocolController(SdmHandOverProtocolService service) {
		this.sdmHandOverProtocolService = service;
	}

	@PostMapping
	@Operation(summary = "Erstellt ein neues Übergabeprotokoll", description = "Erstellt ein Übergabeprotokoll anhand der übergebenen Daten.", operationId = "createHandoverProtocol", responses = {
			@ApiResponse(responseCode = "201", description = "Protokoll erfolgreich erstellt", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmHandOverProtocolTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Protokolldaten", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmHandOverProtocolTo> createProtocol(@Valid @RequestBody SdmHandOverProtocolTo request) {
		SdmHandOverProtocolTo saved = sdmHandOverProtocolService.saveHandOverProtocol(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	/**
	 * Get ALL handover protocols for a given device serial number
	 */
	@GetMapping("/device/{serialNumber}")
	@Operation(summary = "Liefert alle Protokolle für ein Gerät", description = "Ruft alle Übergabeprotokolle für die angegebene Seriennummer ab.", operationId = "getProtocolsByDeviceSerial", responses = {
			@ApiResponse(responseCode = "200", description = "Protokolle erfolgreich abgerufen", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmHandOverProtocolTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Seriennummer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<List<SdmHandOverProtocolTo>> getProtocolsByDeviceSerialNumber(
			@PathVariable @NotBlank @Size(max = 50) String serialNumber) {
		List<SdmHandOverProtocolTo> handoverProtocols = sdmHandOverProtocolService
				.findByDeviceSerialNumber(serialNumber);
		return ResponseEntity.ok(handoverProtocols);
	}

	/**
	 * Get the LATEST handover protocol for a device
	 */
	@GetMapping("/device/{serialNumber}/latest")
	@Operation(summary = "Liefert das neueste Protokoll für ein Gerät", description = "Ruft das zuletzt erstellte Übergabeprotokoll für die angegebene Seriennummer ab.", operationId = "getLatestProtocolByDeviceSerial", responses = {
			@ApiResponse(responseCode = "200", description = "Neuestes Protokoll erfolgreich abgerufen", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmHandOverProtocolTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Seriennummer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Kein Protokoll gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmHandOverProtocolTo> getLatestProtocol(
			@PathVariable @NotBlank @Size(max = 50) String serialNumber) {
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
	@Operation(summary = "Bestätigt das neueste unbestätigte Protokoll für ein Gerät", description = "Markiert das neueste unbestätigte Übergabeprotokoll für die angegebene Seriennummer als bestätigt.", operationId = "confirmLatestProtocol", responses = {
			@ApiResponse(responseCode = "200", description = "Protokoll erfolgreich bestätigt", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmHandOverProtocolTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Seriennummer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Kein unbestätigtes Protokoll gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmHandOverProtocolTo> confirmLatestUnconfirmed(
			@PathVariable @NotBlank @Size(max = 50) String serialNumber) {
		SdmHandOverProtocolTo confirmed = sdmHandOverProtocolService.confirmByDeviceSerialNumber(serialNumber);
		return ResponseEntity.ok(confirmed);
	}

	@GetMapping("/receiver/{userId}")
	@Operation(
			summary = "Liefert alle Protokolle für einen Empfänger",
			description = "Ruft alle Übergabeprotokolle für die angegebene User-ID des Empfängers ab.",
			operationId = "getProtocolsByReceiverUserId",
			responses = {
					@ApiResponse(responseCode = "200",
							description = "Protokolle erfolgreich abgerufen",
							content = @Content(mediaType = "application/json",
									schema = @Schema(implementation = SdmHandOverProtocolTo.class))),
					@ApiResponse(responseCode = "400",
							description = "Ungültige User-ID",
							content = @Content(mediaType = "application/json",
									schema = @Schema(implementation = SdmErrorResponse.class))),
					@ApiResponse(responseCode = "404",
							description = "Keine Protokolle gefunden",
							content = @Content(mediaType = "application/json",
									schema = @Schema(implementation = SdmErrorResponse.class)))
			})
	public ResponseEntity<List<SdmHandOverProtocolTo>> getProtocolsByReceiverUserId(
			@PathVariable
			@NotNull
			@Parameter(description = "User-ID des Empfängers (UUID)")
			UUID userId) {  // ✅ Changed to UUID

		List<SdmHandOverProtocolTo> handoverProtocols =
				sdmHandOverProtocolService.findHandOverProtocolsByReceiverUserId(userId);  // ✅ Pass UUID

		return ResponseEntity.ok(handoverProtocols);
	}
}
