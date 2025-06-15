package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmUserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<SdmUserEntity> users = sdmUserService.getAllUsers();
        List<SdmUserTo> sdmUserTos = sdmUserMapper.toToList(users);
        return ResponseEntity.ok(sdmUserTos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SdmUserTo> getUserById(@PathVariable Long id) {
        log.debug("Fetching user with id: {}", id);
        SdmUserEntity user = sdmUserService.getUserById(id);
        if (user == null) {
            log.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sdmUserMapper.toTo(user));
    }

    @PostMapping
    public ResponseEntity<SdmUserTo> createUser(@RequestBody SdmUserTo sdmUserTo) {
        log.debug("Creating new user: {}", sdmUserTo);
        
        SdmUserEntity sdmUserEntity = sdmUserMapper.toEntity(sdmUserTo);
        log.debug("Mapped user entity: {}", sdmUserEntity);

        SdmUserEntity savedUser = sdmUserService.saveUser(sdmUserEntity);
        log.debug("Saved user: {}", savedUser);

        SdmUserTo savedUserTo = sdmUserMapper.toTo(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserTo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SdmUserTo> updateUser(@PathVariable Long id, @RequestBody SdmUserTo sdmUserTo) {
        log.debug("Updating user with id: {}, data: {}", id, sdmUserTo);
        
        SdmUserEntity existingUser = sdmUserService.getUserById(id);
        if (existingUser == null) {
            log.warn("User with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }

        SdmUserEntity sdmUserEntity = sdmUserMapper.toEntity(sdmUserTo);
        sdmUserEntity.setId(id); // Ensure the ID is set for update
        
        SdmUserEntity updatedUser = sdmUserService.saveUser(sdmUserEntity);
        log.debug("Updated user: {}", updatedUser);

        return ResponseEntity.ok(sdmUserMapper.toTo(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("Deleting user with id: {}", id);
        
        SdmUserEntity user = sdmUserService.getUserById(id);
        if (user == null) {
            log.warn("User with id {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }

        sdmUserService.deleteUser(id);
        log.info("Deleted user with id: {}", id);
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/department/{departmentName}")
    public ResponseEntity<List<SdmUserTo>> getUsersByDepartment(@PathVariable  String departmentName) {
    	
    	List<SdmUserEntity> users = sdmUserService.getUsersByDepartmentName(departmentName);
    	List<SdmUserTo> usersTO = sdmUserMapper.toToList(users);
    	return ResponseEntity.ok(usersTO);
    	
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<List<SdmUserTo>> getUsersByUsername(@PathVariable String username) {
    	List<SdmUserEntity> users = sdmUserService.getUserByUsername(username);
    	List<SdmUserTo> sdmUserTos = sdmUserMapper.toToList(users);
    	return ResponseEntity.ok(sdmUserTos);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<SdmUserTo>> getUsersByname(@PathVariable String name) {
    	List<SdmUserEntity> users = sdmUserService.getUserByName(name);
    	List<SdmUserTo> sdmUserTos = sdmUserMapper.toToList(users);
    	return ResponseEntity.ok(sdmUserTos);
    }
    
    @PutMapping("/assign-department")
    public ResponseEntity<SdmUserTo> assignDepartment(@RequestParam String username,
                                                   @RequestParam String departmentName) {
        SdmUserEntity updatedUser = sdmUserService.assignDepartmentToUser(username, departmentName);
        return ResponseEntity.ok(sdmUserMapper.toTo(updatedUser));
    }
}