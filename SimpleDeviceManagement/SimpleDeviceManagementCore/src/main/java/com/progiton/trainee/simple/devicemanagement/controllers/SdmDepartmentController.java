package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmDepartmentMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class SdmDepartmentController {

    private final SdmDepartmentService sdmDepartmentService;
    private final SdmDepartmentMapper sdmDepartmentMapper;

    public SdmDepartmentController(SdmDepartmentService sdmDepartmentService, SdmDepartmentMapper sdmDepartmentMapper) {
        this.sdmDepartmentService = sdmDepartmentService;
        this.sdmDepartmentMapper = sdmDepartmentMapper;
    }

    @GetMapping
    public List<SdmDepartmentTo> getAllDepartments() {
        return sdmDepartmentMapper.toToList(sdmDepartmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SdmDepartmentTo> getDepartmentById(@PathVariable Long id) {
        SdmDepartmentEntity entity = sdmDepartmentService.getDepartmentById(id);
        return ResponseEntity.ok(sdmDepartmentMapper.toTo(entity));
    }

    @PostMapping
    public ResponseEntity<SdmDepartmentTo> createDepartment(@RequestBody SdmDepartmentTo dto) {
        SdmDepartmentEntity created = sdmDepartmentService.saveDepartment(sdmDepartmentMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(sdmDepartmentMapper.toTo(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SdmDepartmentTo> updateDepartment(@PathVariable Long id, @RequestBody SdmDepartmentTo dto) {
        SdmDepartmentEntity updated = sdmDepartmentService.updateDepartment(id, sdmDepartmentMapper.toEntity(dto));
        return ResponseEntity.ok(sdmDepartmentMapper.toTo(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        sdmDepartmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
