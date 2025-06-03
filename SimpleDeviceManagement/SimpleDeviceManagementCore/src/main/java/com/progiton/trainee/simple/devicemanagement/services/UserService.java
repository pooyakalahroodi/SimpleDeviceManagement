package com.progiton.trainee.simple.devicemanagement.services;
import com.progiton.trainee.simple.devicemanagement.model.UserEntity;
import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    UserEntity getUserByUsername(String username);
    List<UserEntity> getUsersByDepartment(Long departmentId);
    UserEntity saveUser(UserEntity userEntity);
    UserEntity createUser(UserEntity userEntity); // Add this
    UserEntity updateUser(Long id, UserEntity updatedUser);
    void deleteUser(Long id);
    UserEntity assignRoleToUser(Long userId, Long roleId);
    UserEntity removeRoleFromUser(Long userId, Long roleId);
    UserEntity assignDepartmentToUser(Long userId, Long departmentId);
}
