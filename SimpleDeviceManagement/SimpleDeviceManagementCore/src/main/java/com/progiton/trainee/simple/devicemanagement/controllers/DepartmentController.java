package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.model.to.DepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.services.DepartmentService;
import com.progiton.trainee.simple.devicemanagement.mapper.DepartmentMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService, DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping
    public List<DepartmentTo> getAllDepartments() {
        return departmentMapper.toToList(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentTo> getDepartmentById(@PathVariable Long id) {
        DepartmentEntity entity = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentMapper.toTo(entity));
    }

    @PostMapping
    public ResponseEntity<DepartmentTo> createDepartment(@RequestBody DepartmentTo dto) {
        DepartmentEntity created = departmentService.saveDepartment(departmentMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentMapper.toTo(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentTo> updateDepartment(@PathVariable Long id, @RequestBody DepartmentTo dto) {
        DepartmentEntity updated = departmentService.updateDepartment(id, departmentMapper.toEntity(dto));
        return ResponseEntity.ok(departmentMapper.toTo(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
