package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface SdmUserService {

	// TODO eliminate Entity from interface

	// TODO return SdmUserTo
	List<SdmUserTo> findAllUsers();

	SdmUserTo findUserByUserId(UUID userId);

	List<SdmUserTo> findUsersByDepartmentName(String departmentName);

	List<SdmDeviceTo> findDeviceByUserID(UUID userId);

	SdmUserTo findUserByEmailAddress(@NotBlank @Email String emailAddress);


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

	SdmUserTo assignDepartmentToUser(UUID userId, String departmentName);

	SdmUserTo saveUser(SdmUserTo user);

	SdmUserTo createUser(SdmUserTo request); // Add this

	SdmDeviceTo assignDeviceToUser(UUID userId, String serialNumber);

}
