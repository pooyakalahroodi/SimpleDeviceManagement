package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;

@Service
public class SdmUserService {

	private final SdmUserCoreService sdmUserCoreService;

	public SdmUserService(SdmUserCoreService sdmUserCoreService) {
		this.sdmUserCoreService = sdmUserCoreService;
	}

	public List<SdmUserTo> findAllUsers() {
		return sdmUserCoreService.findAllUsers();
	}

	public SdmUserTo saveUser(SdmUserTo user) {
		return sdmUserCoreService.saveUser(user);
	}

	public SdmUserTo createUser(SdmUserTo request) {
		return sdmUserCoreService.createUser(request);
	}

	public SdmDeviceTo assignDeviceToUser(UUID userId, String serialNumber) {
		return sdmUserCoreService.assignDeviceToUser(userId, serialNumber);
	}

	public SdmUserTo findUserByUserId(UUID username) {
		return sdmUserCoreService.findUserByUserId(username);
	}

	public List<SdmUserTo> findUsersByDepartmentName(String departmentName) {
		return sdmUserCoreService.findUsersByDepartmentName(departmentName);
	}

	public SdmUserTo assignDepartmentToUser(UUID userId, String departmentName) {
		return sdmUserCoreService.assignDepartmentToUser(userId, departmentName);
	}

	public List<SdmDeviceTo> findDeviceByUserID(UUID userId) {
		return sdmUserCoreService.findDeviceByUser(userId);
	}

	public SdmUserTo findUserByEmailAddress(String emailAddress) {
		return sdmUserCoreService.findUserByEmailAddress(emailAddress);
	}

}
