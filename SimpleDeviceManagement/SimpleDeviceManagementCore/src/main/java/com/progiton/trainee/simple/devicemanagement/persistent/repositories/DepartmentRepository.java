package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DepartmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

	Optional<DepartmentEntity> findByNameIgnoreCase(String name);
}
