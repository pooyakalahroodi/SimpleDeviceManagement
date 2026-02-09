package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;

@Repository
public interface SdmUserRepository extends JpaRepository<SdmUserEntity, Long> {

	
    List<SdmUserEntity> findByDepartmentNameIgnoreCase(String departmentName);
	Optional<SdmUserEntity> findByUserId(UUID userId);
    List<SdmUserEntity> findByNameIgnoreCase(String name);
    boolean existsByUserId(UUID userId);

    
}