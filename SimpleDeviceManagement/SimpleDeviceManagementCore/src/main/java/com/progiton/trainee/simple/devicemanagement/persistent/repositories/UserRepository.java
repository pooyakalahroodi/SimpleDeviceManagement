package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	

	List<UserEntity> findByDepartmentEntityId(Long departmentId);
	
	List<UserEntity> findByDepartmentEntityNameIgnoreCase(String departmentName);
	List<UserEntity> findByName(String name);
	List<UserEntity> findByUsernameStartingWithIgnoreCase(String username);
	List<UserEntity> findByNameStartingWithIgnoreCase(String name);


}
