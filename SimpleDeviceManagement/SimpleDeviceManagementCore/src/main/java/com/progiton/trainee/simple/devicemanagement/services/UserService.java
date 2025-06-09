package com.progiton.trainee.simple.devicemanagement.services;
import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    List<UserEntity> getUsersByDepartment(Long departmentId);
    
    
    List<UserEntity> getUserByUsername(String username);
    List<UserEntity> getUserByName(String name);
    List<UserEntity> getUsersByDepartmentName(String departmentName);
    UserEntity assignDepartmentToUser(String username, String departmentName);

    
    
    
    UserEntity saveUser(UserEntity userEntity);
    UserEntity createUser(UserEntity userEntity); // Add this
    UserEntity updateUser(Long id, UserEntity updatedUser);
    void deleteUser(Long id);
    UserEntity assignRoleToUser(Long userId, Long roleId);
    UserEntity removeRoleFromUser(Long userId, Long roleId);
    UserEntity assignDepartmentToUser(Long userId, Long departmentId);
}
