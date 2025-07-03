package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.progiton.trainee.simple.devicemanagement.mapper.SdmUserMapper;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/users")
public class SdmUserController {

	private static final Logger log = LoggerFactory.getLogger(SdmUserController.class);

	private final SdmUserService sdmUserService;
	private final SdmUserMapper sdmUserMapper;

	public SdmUserController(SdmUserService sdmUserService, SdmUserMapper sdmUserMapper) {
		this.sdmUserService = sdmUserService;
		this.sdmUserMapper = sdmUserMapper;

	}

	@GetMapping
	public ResponseEntity<List<SdmUserTo>> getAllUsers() {
		log.debug("Fetching all users");
		List<SdmUserTo> users = sdmUserService.findAllUsers();
		return ResponseEntity.ok(users);
	}

	@PostMapping
	public ResponseEntity<SdmUserTo> createUser(@RequestBody SdmUserTo sdmUserTo) {
		log.debug("Creating new user: {}", sdmUserTo);

		SdmUserTo savedUser = sdmUserService.saveUser(sdmUserTo);
		log.debug("Saved user: {}", savedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	@GetMapping("/department/{departmentName}")
	public ResponseEntity<List<SdmUserTo>> getUsersByDepartment(@PathVariable String departmentName) {

		List<SdmUserTo> users = sdmUserService.findUsersByDepartmentName(departmentName);

		return ResponseEntity.ok(users);
	}

	@Operation(description = "Liefert ein User für gegebene Name", operationId = "getUsersByUsername", responses = {
			@ApiResponse(responseCode = "400", description = "Bas Request bei invalid username", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found for given username", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SdmErrorResponse.class)) }),
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SdmUserEntity.class)) }), })

	@GetMapping("/username/{username}")
	// TODO definde Response-Class for
	public ResponseEntity<SdmUserTo> getUsersByUsername(
			@PathVariable @Valid @Size(min = 6, max = 255) String username) {

		// Objects.requireNonNull(username, "Message .... ");

		SdmUserTo user = sdmUserService.findUserByUsername(username);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{username}/devices/{serialNumber}/assign")
	@ResponseStatus(HttpStatus.OK)
	public SdmDeviceTo assignDeviceToUser(@PathVariable String username, @PathVariable String serialNumber) {
		return sdmUserService.assignDeviceToUser(username, serialNumber);
	}

//	@GetMapping("/name/{name}")
//	public ResponseEntity<List<SdmUserEntity>> getUsersByname(@PathVariable String name) {
//		List<SdmUserEntity> users = sdmUserService.findUserBysurame(name);
//		List<SdmUserEntity> sdmUserEntities = sdmUserMapper.toToList(users);
//		return ResponseEntity.ok(sdmUserEntities);
//	}

	@PutMapping("/assign-department")
	public ResponseEntity<SdmUserTo> assignDepartment(@RequestParam String username,
			@RequestParam String departmentName) {

		SdmUserTo user = sdmUserService.findUserByUsername(username);
		SdmUserTo updatedUser = sdmUserService.assignDepartmentToUser(username, departmentName);
		return ResponseEntity.ok(updatedUser);
	}
}