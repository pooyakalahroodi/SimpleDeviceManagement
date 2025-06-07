package com.progiton.trainee.simple.devicemanagement.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.services.UserService;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.DepartmentRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final DepartmentRepository departmentRepository;
	
	public UserServiceImpl(UserRepository userRepository, DepartmentRepository departmentRepository) {
	    this.userRepository = userRepository;
	    this.departmentRepository = departmentRepository;
	}
	
	@Override
	public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
	}

	@Override
	public UserEntity getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return user.get();		
	}

	@Override
	public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
	}

	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with username: " + username);
        }
        return user.get();
	}

	@Override
	public List<UserEntity> getUsersByDepartment(Long departmentId) {
        return userRepository.findByDepartmentEntityId(departmentId);

	}

	@Override
	public UserEntity updateUser(Long id, UserEntity updatedUser) {
		UserEntity existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setDepartmentEntity(updatedUser.getDepartmentEntity());
        existingUser.setRoleEntities(updatedUser.getRoleEntities());
        return userRepository.save(existingUser);
    }
	

	@Override
	public UserEntity assignRoleToUser(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity removeRoleFromUser(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity assignDepartmentToUser(Long userId, Long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity createUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return null;
	}
}
