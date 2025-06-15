package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SdmDepartmentRepository extends JpaRepository<SdmDepartmentEntity, Long>{

	Optional<SdmDepartmentEntity> findByNameIgnoreCase(String name);
}
