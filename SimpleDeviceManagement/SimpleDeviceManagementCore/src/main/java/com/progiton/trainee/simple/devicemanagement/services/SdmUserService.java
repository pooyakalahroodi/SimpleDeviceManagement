package com.progiton.trainee.simple.devicemanagement.services;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import java.util.List;

public interface SdmUserService {
    List<SdmUserEntity> getAllUsers();
    SdmUserEntity getUserById(Long id);
    List<SdmUserEntity> getUsersByDepartment(Long departmentId);
    
    
    List<SdmUserEntity> getUserByUsername(String username);
    List<SdmUserEntity> getUserByName(String name);
    List<SdmUserEntity> getUsersByDepartmentName(String departmentName);
    SdmUserEntity assignDepartmentToUser(String username, String departmentName);

    
    
    
    SdmUserEntity saveUser(SdmUserEntity sdmUserEntity);
    SdmUserEntity createUser(SdmUserEntity sdmUserEntity); // Add this
    SdmUserEntity updateUser(Long id, SdmUserEntity updatedUser);
    void deleteUser(Long id);
    SdmUserEntity assignRoleToUser(Long userId, Long roleId);
    SdmUserEntity removeRoleFromUser(Long userId, Long roleId);
    SdmUserEntity assignDepartmentToUser(Long userId, Long departmentId);
}
