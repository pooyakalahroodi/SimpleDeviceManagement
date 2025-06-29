package com.progiton.trainee.simple.devicemanagement.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDepartmentRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmUserRepository;

@Service
public class SdmUserServiceImpl implements SdmUserService{
	
	private final SdmUserRepository sdmUserRepository;
	private final SdmDepartmentRepository sdmDepartmentRepository;
	
	public SdmUserServiceImpl(SdmUserRepository sdmUserRepository, SdmDepartmentRepository sdmDepartmentRepository) {
	    this.sdmUserRepository = sdmUserRepository;
	    this.sdmDepartmentRepository = sdmDepartmentRepository;
	}
	
	@Override
	public List<SdmUserEntity> getAllUsers() {
        return sdmUserRepository.findAll();
	}

	@Override
	public SdmUserEntity getUserById(Long id) {
        Optional<SdmUserEntity> user = sdmUserRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return user.get();		
	}

	@Override
	public SdmUserEntity saveUser(SdmUserEntity sdmUserEntity) {
        return sdmUserRepository.save(sdmUserEntity);
	}

	@Override
	public void deleteUser(Long id) {
		if (!sdmUserRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        sdmUserRepository.deleteById(id);
	}
	
	// finding users by username

	@Override
	public List<SdmUserEntity> getUserByUsername(String username) {
		List<SdmUserEntity> users = sdmUserRepository.findByUsernameStartingWithIgnoreCase(username);
        return users;
	}

	// filtering users by departmentId
	
	
	@Override
	public List<SdmUserEntity> getUsersByDepartment(Long departmentId) {
        return sdmUserRepository.findBySdmDepartmentEntityId(departmentId);

	}

	// filtering users by departmentName

	@Override
	public List<SdmUserEntity> getUsersByDepartmentName(String departmentName) {
		return sdmUserRepository.findBySdmDepartmentEntityNameIgnoreCase(departmentName);
	}
	
	// finding users by name
	
	@Override
	public List<SdmUserEntity> getUserByName(String name) {
		return sdmUserRepository.findByNameStartingWithIgnoreCase(name);
	}

	@Override
	public SdmUserEntity updateUser(Long id, SdmUserEntity updatedUser) {
		SdmUserEntity existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setDepartment(updatedUser.getDepartment());
        existingUser.setRoles(updatedUser.getRoles());
        return sdmUserRepository.save(existingUser);
    }
	
	@Override
	public SdmUserEntity assignDepartmentToUser(String username, String departmentName) {
		 // Find user
	    List<SdmUserEntity> users = sdmUserRepository.findByUsernameStartingWithIgnoreCase(username);
	    SdmUserEntity user = users.get(0); // Use first one

	    // Find department
	    SdmDepartmentEntity department = sdmDepartmentRepository.findByNameIgnoreCase(departmentName)
	        .orElseThrow(() -> new RuntimeException("Department not found with name: " + departmentName));

	    // Assign department and save
	    user.setDepartment(department);
	    return sdmUserRepository.save(user);
	}
	

	@Override
	public SdmUserEntity assignRoleToUser(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdmUserEntity removeRoleFromUser(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdmUserEntity assignDepartmentToUser(Long userId, Long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdmUserEntity createUser(SdmUserEntity sdmUserEntity) {
		// TODO Auto-generated method stub
		return null;
	}






	
}
