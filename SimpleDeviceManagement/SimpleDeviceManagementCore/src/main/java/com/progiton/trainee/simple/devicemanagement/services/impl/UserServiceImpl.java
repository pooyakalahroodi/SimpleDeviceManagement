package com.progiton.trainee.simple.devicemanagement.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.services.UserService;
import com.progiton.trainee.simple.devicemanagement.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		
		this.userRepository = userRepository;	
	}

	@Override
	public List<UserEntity> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity saveUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserEntity> getUsersByDepartment(Long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity updateUser(Long id, UserEntity updatedUser) {
		// TODO Auto-generated method stub
		return null;
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
