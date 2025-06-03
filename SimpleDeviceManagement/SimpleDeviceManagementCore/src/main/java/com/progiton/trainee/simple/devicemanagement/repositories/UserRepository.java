package com.progiton.trainee.simple.devicemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.progiton.trainee.simple.devicemanagement.model.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
