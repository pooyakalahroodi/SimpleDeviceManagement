package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.mapper.UserMapper;
import com.progiton.trainee.simple.devicemanagement.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.services.UserService;
import com.progiton.trainee.simple.devicemanagement.to.UserTo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<UserTo> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserTo> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserTo> createUser(@RequestBody UserTo userTo) {
        UserEntity userEntity = userMapper.toEntity(userTo);
        UserEntity createdUser = userService.createUser(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(createdUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserTo> updateUser(@PathVariable Long id, @RequestBody UserTo userTo) {
        UserEntity userEntity = userMapper.toEntity(userTo);
        UserEntity updatedUser = userService.updateUser(id, userEntity);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-department/{departmentId}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<UserTo> getUsersByDepartment(@PathVariable Long departmentId) {
        return userService.getUsersByDepartment(departmentId).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-username/{username}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserTo> getUserByUsername(@PathVariable String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserTo> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        UserEntity updatedUser = userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @DeleteMapping("/{userId}/remove-role/{roleId}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserTo> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        UserEntity updatedUser = userService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @PostMapping("/{userId}/assign-department/{departmentId}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserTo> assignDepartmentToUser(@PathVariable Long userId, @PathVariable Long departmentId) {
        UserEntity updatedUser = userService.assignDepartmentToUser(userId, departmentId);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
}