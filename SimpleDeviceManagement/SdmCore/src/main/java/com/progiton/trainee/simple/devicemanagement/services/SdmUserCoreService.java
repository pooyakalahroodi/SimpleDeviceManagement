package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;

public interface SdmUserCoreService {

	List<SdmUserTo> findAllUsers();

	SdmUserTo findUserByUsername(String username);

	List<SdmUserTo> findUsersByDepartmentName(String departmentName);

	List<SdmDeviceTo> findDeviceByUser(String username);

	SdmUserTo assignDepartmentToUser(String username, String departmentName);

	SdmUserTo saveUser(SdmUserTo user);

	SdmUserTo createUser(SdmUserTo request);

	SdmDeviceTo assignDeviceToUser(String username, String serialNumber);

}
