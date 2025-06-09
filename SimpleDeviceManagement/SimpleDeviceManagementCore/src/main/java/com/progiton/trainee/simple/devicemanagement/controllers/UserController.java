package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.services.UserService;
import com.progiton.trainee.simple.devicemanagement.model.to.UserTo;
import com.progiton.trainee.simple.devicemanagement.mapper.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserTo>> getAllUsers() {
        log.debug("Fetching all users");
        List<UserEntity> users = userService.getAllUsers();
        List<UserTo> userTos = userMapper.toToList(users);
        return ResponseEntity.ok(userTos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTo> getUserById(@PathVariable Long id) {
        log.debug("Fetching user with id: {}", id);
        UserEntity user = userService.getUserById(id);
        if (user == null) {
            log.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toTo(user));
    }

    @PostMapping
    public ResponseEntity<UserTo> createUser(@RequestBody UserTo userTo) {
        log.debug("Creating new user: {}", userTo);
        
        UserEntity userEntity = userMapper.toEntity(userTo);
        log.debug("Mapped user entity: {}", userEntity);

        UserEntity savedUser = userService.saveUser(userEntity);
        log.debug("Saved user: {}", savedUser);

        UserTo savedUserTo = userMapper.toTo(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserTo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTo> updateUser(@PathVariable Long id, @RequestBody UserTo userTo) {
        log.debug("Updating user with id: {}, data: {}", id, userTo);
        
        UserEntity existingUser = userService.getUserById(id);
        if (existingUser == null) {
            log.warn("User with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }

        UserEntity userEntity = userMapper.toEntity(userTo);
        userEntity.setId(id); // Ensure the ID is set for update
        
        UserEntity updatedUser = userService.saveUser(userEntity);
        log.debug("Updated user: {}", updatedUser);

        return ResponseEntity.ok(userMapper.toTo(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("Deleting user with id: {}", id);
        
        UserEntity user = userService.getUserById(id);
        if (user == null) {
            log.warn("User with id {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(id);
        log.info("Deleted user with id: {}", id);
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/department/{departmentName}")
    public ResponseEntity<List<UserTo>> getUsersByDepartment(@PathVariable  String departmentName) {
    	
    	List<UserEntity> users = userService.getUsersByDepartmentName(departmentName);
    	List<UserTo> usersTO = userMapper.toToList(users);
    	return ResponseEntity.ok(usersTO);
    	
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<List<UserTo>> getUsersByUsername(@PathVariable String username) {
    	List<UserEntity> users = userService.getUserByUsername(username);
    	List<UserTo> userTos = userMapper.toToList(users);
    	return ResponseEntity.ok(userTos);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserTo>> getUsersByname(@PathVariable String name) {
    	List<UserEntity> users = userService.getUserByName(name);
    	List<UserTo> userTos = userMapper.toToList(users);
    	return ResponseEntity.ok(userTos);
    }
    
    @PutMapping("/assign-department")
    public ResponseEntity<UserTo> assignDepartment(@RequestParam String username,
                                                   @RequestParam String departmentName) {
        UserEntity updatedUser = userService.assignDepartmentToUser(username, departmentName);
        return ResponseEntity.ok(userMapper.toTo(updatedUser));
    }
}