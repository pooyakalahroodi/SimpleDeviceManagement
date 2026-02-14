package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;

@Repository
public interface SdmUserRepository extends JpaRepository<SdmUserEntity, Long> {

	
    List<SdmUserEntity> findByDepartmentNameIgnoreCase(String departmentName);
	Optional<SdmUserEntity> findByUserId(UUID userId);
    Optional<SdmUserEntity> findUserByEmailAddress(@NotBlank @Email String emailAddress);
    List<SdmUserEntity> findByNameIgnoreCase(String name);
    boolean existsByUserId(UUID userId);

    
}