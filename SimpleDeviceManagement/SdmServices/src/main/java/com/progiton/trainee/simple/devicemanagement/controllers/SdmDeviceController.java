package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityAlreadyExistsException;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmErrorResponse;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/api/devices")
public class SdmDeviceController {

	private final SdmDeviceService sdmDeviceService;

//    public DeviceController(DeviceService deviceService, DeviceMapper deviceMapper) {
//        this.deviceService = deviceService;
//        this.deviceMapper = deviceMapper;
//    }

	public SdmDeviceController(SdmDeviceService sdmDeviceService) {
		this.sdmDeviceService = sdmDeviceService;
	}

	@GetMapping
	@Operation(summary = "Liefert alle Geräte", description = "Ruft die Liste aller gespeicherten Geräte ab.", operationId = "getAllDevices", responses = {
			@ApiResponse(responseCode = "200", description = "Liste erfolgreich abgerufen", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmDeviceTo.class))) })
	public List<SdmDeviceTo> getAllDevices() {
		return sdmDeviceService.findAllDevices();
	}

	@PostMapping
	@Operation(summary = "Speichert ein neues Gerät", description = "Speichert ein Gerät basierend auf den übergebenen Daten.", operationId = "saveDevice", responses = {
			@ApiResponse(responseCode = "200", description = "Gerät erfolgreich gespeichert", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmDeviceTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Gerätedaten", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmDeviceTo> saveDevice(@Valid @RequestBody SdmDeviceTo device) {
		SdmDeviceTo saved = sdmDeviceService.saveDevice(device);
		return ResponseEntity.ok(saved);
	}
	@ExceptionHandler({ SdmEntityAlreadyExistsException.class })
	public ResponseEntity<String> handleSdmEntityAlreadyExistsException(HttpServletRequest request, SdmEntityAlreadyExistsException e) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(String.format("\"Error\": \"%s\"", e.getMessage()));
	}

		// Assigning Device to User
//
//	@PutMapping("/assign-device")
//	public ResponseEntity<SdmDeviceTo> assignDeviceToUser(@RequestParam String serialNumber,
//			@RequestParam String username) {
//		SdmDeviceTo updatedDevice = sdmDeviceService.assignDeviceToUser(serialNumber, username);
//		return ResponseEntity.ok(sdmDeviceMapper.toTo(updatedDevice));
//	}

	@PutMapping("/update-status")
	@Operation(summary = "Aktualisiert den Status eines Geräts", description = "Ändert den Status eines Geräts anhand der Seriennummer.", operationId = "updateDeviceStatus", responses = {
			@ApiResponse(responseCode = "200", description = "Status erfolgreich aktualisiert", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmDeviceTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Parameter", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Gerät nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmDeviceTo> updateDeviceStatus(@RequestParam @NotBlank @Size(max = 50) String serialNumber,
			@RequestParam @NotNull SdmDeviceStatus status) {
		SdmDeviceTo updated = sdmDeviceService.updateDeviceStatus(serialNumber, status);
		return ResponseEntity.ok(updated);
	}
}
