package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmErrorResponse;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/api/users")
public class SdmUserController {

	private static final Logger log = LoggerFactory.getLogger(SdmUserController.class);

	private final SdmUserService sdmUserService;

	public SdmUserController(SdmUserService sdmUserService) {
		this.sdmUserService = sdmUserService;

	}

	@GetMapping
	public ResponseEntity<List<SdmUserTo>> getAllUsers() {
		log.debug("Fetching all users");
		List<SdmUserTo> users = sdmUserService.findAllUsers();
		return ResponseEntity.ok(users);
	}

	@PostMapping
	@Operation(summary = "Erstellt einen neuen Benutzer", description = "Erstellt einen neuen Benutzer basierend auf den übergebenen Daten.", operationId = "createUser", responses = {
			@ApiResponse(responseCode = "201", description = "Benutzer erfolgreich erstellt", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmUserTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültige Benutzerdaten", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "Benutzername bereits vergeben", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmUserTo> createUser(@Valid @RequestBody SdmUserTo sdmUserTo) {
		log.debug("Creating new user: {}", sdmUserTo);

		SdmUserTo savedUser = sdmUserService.createUser(sdmUserTo);
		log.debug("Saved user: {}", savedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	@GetMapping("/department/{departmentName}")
	@Operation(summary = "Findet alle Benutzer in einer Abteilung", description = "Liefert eine Liste aller Benutzer, die der angegebenen Abteilung zugeordnet sind.", operationId = "getUsersByDepartment", responses = {
			@ApiResponse(responseCode = "200", description = "Benutzerliste erfolgreich abgerufen", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmUserTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültiger Abteilungsname", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Keine Benutzer für die angegebene Abteilung gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<List<SdmUserTo>> getUsersByDepartment(
			@PathVariable @NotBlank @Size(max = 100) String departmentName) {

		List<SdmUserTo> users = sdmUserService.findUsersByDepartmentName(departmentName);

		return ResponseEntity.ok(users);
	}

	@GetMapping("/username/{username}")
	@Operation(description = "Liefert ein User für gegebene Name", operationId = "getUsersByUsername", responses = {
			@ApiResponse(responseCode = "400", description = "Bas Request bei invalid username", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found for given username", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class)) }),
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SdmUserTo.class)) }), })
	// TODO definde Response-Class for
	public ResponseEntity<SdmUserTo> getUsersByUsername(
			@PathVariable @Valid @Size(min = 6, max = 255) String username) {

		// Objects.requireNonNull(username, "Message .... ");

		SdmUserTo user = sdmUserService.findUserByUsername(username);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{username}/devices/{serialNumber}/assign")
	@Operation(summary = "Weist ein Gerät einem Benutzer zu", description = "Verknüpft ein Gerät anhand der Seriennummer mit dem angegebenen Benutzer.", operationId = "assignDeviceToUser", responses = {
			@ApiResponse(responseCode = "200", description = "Gerät erfolgreich zugewiesen", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmDeviceTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültiger Benutzername oder Seriennummer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Benutzer oder Gerät nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	@ResponseStatus(HttpStatus.OK)
	public SdmDeviceTo assignDeviceToUser(@PathVariable @NotBlank @Size(min = 6, max = 255) String username,
			@PathVariable @NotBlank @Size(max = 50) String serialNumber) {
		return sdmUserService.assignDeviceToUser(username,serialNumber);
	}

//	@GetMapping("/name/{name}")
//	public ResponseEntity<List<SdmUserEntity>> getUsersByname(@PathVariable String name) {
//		List<SdmUserEntity> users = sdmUserService.findUserBysurame(name);
//		List<SdmUserEntity> sdmUserEntities = sdmUserMapper.toToList(users);
//		return ResponseEntity.ok(sdmUserEntities);
//	}

	@PutMapping("/assign-department")
	@Operation(summary = "Weist einen Benutzer einer Abteilung zu", description = "Ordnet einen Benutzer anhand des Benutzernamens einer angegebenen Abteilung zu.", operationId = "assignDepartmentToUser", responses = {
			@ApiResponse(responseCode = "200", description = "Benutzer erfolgreich aktualisiert", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmUserTo.class))),
			@ApiResponse(responseCode = "400", description = "Ungültiger Benutzername oder Abteilungsname", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Benutzer oder Abteilung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class))) })
	public ResponseEntity<SdmUserTo> assignDepartment(@RequestParam @NotBlank @Size(min = 6, max = 255) String username,
			@RequestParam @NotBlank @Size(max = 100) String departmentName) {

		SdmUserTo updatedUser = sdmUserService.assignDepartmentToUser(username, departmentName);
		return ResponseEntity.ok(updatedUser);
	}
}