package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserCoreService;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;

@Service
public class SdmUserServiceImpl implements SdmUserService {

	private final SdmUserCoreService sdmUserCoreService;

	public SdmUserServiceImpl(SdmUserCoreService sdmUserCoreService) {
		this.sdmUserCoreService = sdmUserCoreService;
	}

	@Override
	public List<SdmUserTo> findAllUsers() {
		return sdmUserCoreService.findAllUsers();
	}

	@Override
	public SdmUserTo saveUser(SdmUserTo user) {
		return sdmUserCoreService.saveUser(user);
	}

	@Override
	public SdmUserTo createUser(SdmUserTo request) {
		return sdmUserCoreService.createUser(request);
	}

	@Override
	public SdmDeviceTo assignDeviceToUser(UUID userId, String serialNumber) {
		return sdmUserCoreService.assignDeviceToUser(userId, serialNumber);
	}

	@Override
	public SdmUserTo findUserByUserId(UUID username) {
		return sdmUserCoreService.findUserByUserId(username);
	}

	@Override
	public List<SdmUserTo> findUsersByDepartmentName(String departmentName) {
		return sdmUserCoreService.findUsersByDepartmentName(departmentName);
	}

	@Override
	public SdmUserTo assignDepartmentToUser(UUID userId, String departmentName) {
		return sdmUserCoreService.assignDepartmentToUser(userId, departmentName);
	}

	@Override
	public List<SdmDeviceTo> findDeviceByUserID(UUID userId) {
		return sdmUserCoreService.findDeviceByUser(userId);
	}

	@Override
	public SdmUserTo findUserByEmailAddress(String emailAddress) {
		return sdmUserCoreService.findUserByEmailAddress(emailAddress);
	}

}
