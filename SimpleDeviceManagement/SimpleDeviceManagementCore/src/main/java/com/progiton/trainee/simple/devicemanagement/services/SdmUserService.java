package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;

public interface SdmUserService {

	// TODO eliminate Entity from interface

	// TODO return SdmUserTo
	List<SdmUserTo> findAllUsers();

	SdmUserTo findUserByUsername(String name);

	List<SdmUserTo> findUsersByDepartmentName(String departmentName);

	List<SdmDeviceTo> findDeviceByUser(String username);

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

	SdmUserTo assignDepartmentToUser(String username, String departmentName);

	SdmUserTo saveUser(SdmUserTo user);

	SdmUserTo createUser(SdmUserTo request); // Add this

	SdmDeviceTo assignDeviceToUser(String serialNumber, String username);

}
