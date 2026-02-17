package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmErrorResponse;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/departments")
public class SdmDepartmentController {

	private final SdmDepartmentService sdmDepartmentService;

	public SdmDepartmentController(SdmDepartmentService sdmDepartmentService) {
		this.sdmDepartmentService = sdmDepartmentService;
	}

	@GetMapping
	@Operation(summary = "Liefert alle Abteilungen", description = "Ruft eine Liste aller vorhandenen Abteilungen ab.", operationId = "getAllDepartments", responses = {
			@ApiResponse(responseCode = "200", description = "Abteilungen erfolgreich abgerufen", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmDepartmentTo.class))) })
	public List<SdmDepartmentTo> getAllDepartments() {
		return sdmDepartmentService.findAllDepartments();
	}

	@PostMapping
	@Operation(summary = "Erstellt eine neue Abteilung", description = "Speichert eine neue Abteilung basierend auf den übergebenen Daten.", operationId = "createDepartment", responses = {
			@ApiResponse(responseCode = "201", description = "Abteilung erfolgreich erstellt", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmDepartmentTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Abteilungsdaten", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "Abteilung mit diesem Namen existiert bereits", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmDepartmentTo> createDepartment(@Valid @RequestBody SdmDepartmentTo department) {
		SdmDepartmentTo created = sdmDepartmentService.saveDepartment(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}
