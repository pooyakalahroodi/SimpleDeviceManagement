package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;

public interface SdmUserCoreService {

	List<SdmUserTo> findAllUsers();

	SdmUserTo findUserByUserId(UUID userId);

	List<SdmUserTo> findUsersByDepartmentName(String departmentName);

	List<SdmDeviceTo> findDeviceByUser(UUID userId);

	SdmUserTo assignDepartmentToUser(UUID userId, String departmentName);

	SdmUserTo saveUser(SdmUserTo user);

	SdmUserTo createUser(SdmUserTo request);

	SdmDeviceTo assignDeviceToUser(UUID userId, String serialNumber);

}
