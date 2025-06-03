package com.progiton.trainee.simple.devicemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.progiton.trainee.simple.devicemanagement.model.DepartmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
