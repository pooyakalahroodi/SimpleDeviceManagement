package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmRoleEntity;

import java.util.List;
import java.util.Optional;

public interface SdmRoleService {
    List<SdmRoleEntity> getAllRoles();
    Optional<SdmRoleEntity> getRoleByName(String name);
    SdmRoleEntity createRole(SdmRoleEntity sdmRoleEntity); // Optional, but helpful
    List<SdmRoleEntity> findAll(); // explicitly declared
    SdmRoleEntity save(SdmRoleEntity sdmRoleEntity); // explicitly declared
}