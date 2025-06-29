package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.exceptions.ErrorResponse;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmDepartmentService;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;
import com.progiton.trainee.simple.devicemanagement.util.ValidationUtil;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.exceptions.ApiException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmUserMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class SdmUserController {

    private static final Logger log = LoggerFactory.getLogger(SdmUserController.class);
    
    private final SdmUserService sdmUserService;
    private final SdmUserMapper sdmUserMapper;
    private final SdmDepartmentService sdmDepartmentService;

 

    

    public SdmUserController(SdmUserService sdmUserService, SdmUserMapper sdmUserMapper, SdmDepartmentService sdmDepartmentService) {
        this.sdmUserService = sdmUserService;
        this.sdmUserMapper = sdmUserMapper;
		this.sdmDepartmentService = sdmDepartmentService;
        
    }

    @GetMapping
    public ResponseEntity<List<SdmUserTo>> getAllUsers() {
        log.debug("Fetching all users");
        List<SdmUserEntity> users = sdmUserService.getAllUsers();
        if (users == null || users.isEmpty()) {
            log.warn("No users found in the system");
            throw new ApiException("No users found", HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<List<SdmUserTo>> getUsersByDepartment(@PathVariable String departmentName) {
    	
        // Check if department name is valid
    	if (!sdmDepartmentService.departmentExists(departmentName)) {
    	    throw new ApiException("Department '" + departmentName + "' does not exist", HttpStatus.NOT_FOUND);
    	}
    	
        List<SdmUserEntity> users = sdmUserService.getUsersByDepartmentName(departmentName);

        ValidationUtil.throwIfNullOrEmpty(users, "No users found in department: " + departmentName);

        List<SdmUserTo> usersTO = sdmUserMapper.toToList(users);
        return ResponseEntity.ok(usersTO);
    }

    @Operation(description = "Liefert ein User für gegebene Name", operationId = "getUsersByUsername",
            responses = {
                    @ApiResponse(responseCode = "400",
                            description = "Bas Request bei invalid username",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found for given username",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SdmUserTo.class))}),
    })

    @GetMapping("/username/{username}")
    // TODO definde Response-Class for
    public ResponseEntity<SdmUserTo> getUsersByUsername(@PathVariable @Valid @Size(min = 6, max = 255) String username) {

        // Objects.requireNonNull(username, "Message .... ");

    	List<SdmUserEntity> users = sdmUserService.getUserByUsername(username);
        ValidationUtil.throwIfNullOrEmpty(users, "No users found with the username: " + username);
    	List<SdmUserTo> sdmUserTos = sdmUserMapper.toToList(users); // TODO refactor move to service
    	return ResponseEntity.ok(sdmUserTos);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<List<SdmUserTo>> getUsersByname(@PathVariable String name) {
    	List<SdmUserEntity> users = sdmUserService.getUserByName(name);
        ValidationUtil.throwIfNullOrEmpty(users, "No users found with the name: " + name);
    	List<SdmUserTo> sdmUserTos = sdmUserMapper.toToList(users);
    	return ResponseEntity.ok(sdmUserTos);
    }
    
    @PutMapping("/assign-department")
    public ResponseEntity<SdmUserTo> assignDepartment(@RequestParam String username,
                                                   @RequestParam String departmentName) {
    	// Check if department name is valid
    	if (!sdmDepartmentService.departmentExists(departmentName)) {
    	    throw new ApiException("Department '" + departmentName + "' does not exist", HttpStatus.NOT_FOUND);
    	}
    	List<SdmUserEntity> users = sdmUserService.getUserByUsername(username);
        ValidationUtil.throwIfNullOrEmpty(users, "No users found with the username: " + username);
        SdmUserEntity updatedUser = sdmUserService.assignDepartmentToUser(username, departmentName);
        return ResponseEntity.ok(sdmUserMapper.toTo(updatedUser));
    }
}