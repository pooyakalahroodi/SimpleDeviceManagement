package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleEntity> getAllRoles();
    Optional<RoleEntity> getRoleByName(String name);
    RoleEntity createRole(RoleEntity roleEntity); // Optional, but helpful
    List<RoleEntity> findAll(); // explicitly declared
    RoleEntity save(RoleEntity roleEntity); // explicitly declared
}