package com.progiton.trainee.simple.devicemanagement.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.progiton.trainee.simple.devicemanagement.persistent.model.DepartmentEntity;
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
	
	// finding users by username

	@Override
	public List<UserEntity> getUserByUsername(String username) {
		List<UserEntity> users = userRepository.findByUsernameStartingWithIgnoreCase(username);
        return users;
	}

	// filtering users by departmentId
	
	@Override
	public List<UserEntity> getUsersByDepartment(Long departmentId) {
        return userRepository.findByDepartmentEntityId(departmentId);

	}

	// filtering users by departmentName

	@Override
	public List<UserEntity> getUsersByDepartmentName(String departmentName) {
		return userRepository.findByDepartmentEntityNameIgnoreCase(departmentName);
	}
	
	// finding users by name
	
	@Override
	public List<UserEntity> getUserByName(String name) {
		return userRepository.findByNameStartingWithIgnoreCase(name);
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
	public UserEntity assignDepartmentToUser(String username, String departmentName) {
		 // Find user
	    List<UserEntity> users = userRepository.findByUsernameStartingWithIgnoreCase(username);
	    UserEntity user = users.get(0); // Use first one

	    // Find department
	    DepartmentEntity department = departmentRepository.findByNameIgnoreCase(departmentName)
	        .orElseThrow(() -> new RuntimeException("Department not found with name: " + departmentName));

	    // Assign department and save
	    user.setDepartmentEntity(department);
	    return userRepository.save(user);
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
