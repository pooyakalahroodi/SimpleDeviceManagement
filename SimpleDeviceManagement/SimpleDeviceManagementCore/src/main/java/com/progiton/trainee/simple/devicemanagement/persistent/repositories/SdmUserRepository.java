package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;

@Repository
public interface SdmUserRepository extends JpaRepository<SdmUserEntity, Long> {

	
    List<SdmUserEntity> findBySdmDepartmentEntityId(Long departmentId);

    List<SdmUserEntity> findBySdmDepartmentEntityNameIgnoreCase(String departmentName);

    List<SdmUserEntity> findByName(String name);

    List<SdmUserEntity> findByUsernameStartingWithIgnoreCase(String username);

    List<SdmUserEntity> findByNameStartingWithIgnoreCase(String name);
}