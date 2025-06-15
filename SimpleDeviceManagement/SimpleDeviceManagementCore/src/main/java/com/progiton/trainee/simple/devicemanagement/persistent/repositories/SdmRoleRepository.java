package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface SdmRoleRepository extends JpaRepository<SdmRoleEntity, Long> {
    Optional<SdmRoleEntity> findByName(String name);
    
    // Explicit (but already inherited from JpaRepository)
    List<SdmRoleEntity> findAll();
    SdmRoleEntity save(SdmRoleEntity sdmRoleEntity);
}

