package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;

@RestController
@RequestMapping("/api/departments")
public class SdmDepartmentController {

	private final SdmDepartmentService sdmDepartmentService;

	public SdmDepartmentController(SdmDepartmentService sdmDepartmentService) {
		this.sdmDepartmentService = sdmDepartmentService;
	}

	@GetMapping
	public List<SdmDepartmentTo> getAllDepartments() {
		return sdmDepartmentService.findAllDepartments();
	}

	@PostMapping
	public ResponseEntity<SdmDepartmentTo> createDepartment(@RequestBody SdmDepartmentTo department) {
		SdmDepartmentTo created = sdmDepartmentService.saveDepartment(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}
