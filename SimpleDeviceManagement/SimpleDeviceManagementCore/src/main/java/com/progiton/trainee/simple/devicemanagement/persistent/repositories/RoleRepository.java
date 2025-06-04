package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import com.progiton.trainee.simple.devicemanagement.persistent.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    
    // Explicit (but already inherited from JpaRepository)
    List<RoleEntity> findAll();
    RoleEntity save(RoleEntity roleEntity);
}

