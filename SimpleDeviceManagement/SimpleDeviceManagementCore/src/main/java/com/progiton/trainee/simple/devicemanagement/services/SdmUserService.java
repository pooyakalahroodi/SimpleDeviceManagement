package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;

import java.util.List;

public interface SdmUserService {

    // TODO eliminate Entity from interface
    SdmUserEntity getUserById(Long id);

    //TODO return SdmUserTo
    List<SdmUserEntity> getUserByUsername(String username);

    List<SdmUserEntity> getAllUsers();
    List<SdmUserEntity> getUserByName(String name);
//    List<SdmUserTo> getUsersByDepartment(Long departmentId);
    List<SdmUserEntity> getUsersByDepartmentName(String departmentName);

//    TODO .... make it
//    List<SdmUserTo> findUsers(FindUserRequest request);
//
//    /**
//     *
//     * @param user Name of User
//     * @param department NAme of Department
//     * @return
//     */
//    List<SdmUserTo> findUsers(String user, String department);
//

    SdmUserEntity assignDepartmentToUser(String username, String departmentName);

    SdmUserEntity saveUser(SdmUserEntity sdmUserTo);
    SdmUserEntity createUser(SdmUserEntity sdmUserTo); // Add this
    SdmUserEntity updateUser(Long id, SdmUserEntity updatedUser);
    void deleteUser(Long id);
    SdmUserEntity assignRoleToUser(Long userId, Long roleId);
    SdmUserEntity removeRoleFromUser(Long userId, Long roleId);
    SdmUserEntity assignDepartmentToUser(Long userId, Long departmentId);
    
}
