package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

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
	public SdmDeviceTo assignDeviceToUser(String username,String serialNumber) {
		return sdmUserCoreService.assignDeviceToUser(username, serialNumber);
	}

	@Override
	public SdmUserTo findUserByUsername(String username) {
		return sdmUserCoreService.findUserByUsername(username);
	}

	@Override
	public List<SdmUserTo> findUsersByDepartmentName(String departmentName) {
		return sdmUserCoreService.findUsersByDepartmentName(departmentName);
	}

	@Override
	public SdmUserTo assignDepartmentToUser(String username, String departmentName) {
		return sdmUserCoreService.assignDepartmentToUser(username, departmentName);
	}

	@Override
	public List<SdmDeviceTo> findDeviceByUser(String username) {
		return sdmUserCoreService.findDeviceByUser(username);
	}

}
