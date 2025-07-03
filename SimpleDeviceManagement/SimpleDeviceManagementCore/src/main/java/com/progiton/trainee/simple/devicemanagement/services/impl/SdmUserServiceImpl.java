package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityAlreadyExistsException;
import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityNotFoundException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmUserMapper;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDepartmentRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmUserRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;

@Service
public class SdmUserServiceImpl implements SdmUserService {

	private final SdmUserRepository sdmUserRepository;
	private final SdmDepartmentRepository sdmDepartmentRepository;
	private final SdmDeviceRepository sdmDeviceRepository;
	private final SdmUserMapper usermapper;
	private final SdmDeviceMapper devicmapper;

	public SdmUserServiceImpl(SdmUserRepository sdmUserRepository, SdmDepartmentRepository sdmDepartmentRepository,
			SdmDeviceRepository sdmDeviceRepository, SdmUserMapper usermapper, SdmDeviceMapper devicemapper) {
		this.sdmUserRepository = sdmUserRepository;
		this.sdmDepartmentRepository = sdmDepartmentRepository;
		this.sdmDeviceRepository = sdmDeviceRepository;
		this.usermapper = usermapper;
		this.devicmapper = devicemapper;
	}

	@Override
	public List<SdmUserTo> findAllUsers() {
		return usermapper.toToList(sdmUserRepository.findAll());
	}

	@Override
	public SdmUserTo saveUser(SdmUserTo user) {

		SdmUserEntity entity = usermapper.toEntity(user);
		SdmUserEntity saved = sdmUserRepository.save(entity);

		return usermapper.toTo(saved);
	}

	@Override
	public SdmUserTo createUser(SdmUserTo request) {
		// Check if user already exists
		if (sdmUserRepository.existsByUsername(request.getUsername())) {
			throw new SdmEntityAlreadyExistsException(
					"User with username " + request.getUsername() + " already exists");
		}

		// Create new user
		SdmUserEntity user = new SdmUserEntity();
		user.setUsername(request.getUsername());
		user.setName(request.getName());
		user.setSurname(request.getSurname());
		user.setEnabled(request.getEnabled() != null ? request.getEnabled() : true);

		// Set department if provided
		if (request.getDepartment() != null) {
			SdmDepartmentEntity department = sdmDepartmentRepository
					.findByNameIgnoreCase(request.getDepartment().getName())
					.orElseThrow(() -> new SdmEntityAlreadyExistsException(
							"Department not found with name: " + request.getDepartment().getName()));
			user.setDepartment(department);
		}

		// Save entity
		SdmUserEntity savedUser = sdmUserRepository.save(user);

		// Convert to DTO using your mapper
		return usermapper.toTo(savedUser);
	}

	@Override
	public SdmDeviceTo assignDeviceToUser(String serialNumber, String username) {
		// Fetch the device
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new SdmEntityNotFoundException("Device not found with serial: " + serialNumber));
		// Fetch the user
		SdmUserEntity user = sdmUserRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + username));
		// Assign the user to the device
		device.setUser(user);
		// Save the updated device
		SdmDeviceEntity saved = sdmDeviceRepository.save(device);
		// Convert to DTO and return

		return devicmapper.toTo(saved);
	}
	// finding users by username

	@Override
	public SdmUserTo findUserByUsername(String username) {
		SdmUserEntity user = sdmUserRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + username));
		return usermapper.toTo(user);
	}

	// filtering users by departmentName
	@Override
	public List<SdmUserTo> findUsersByDepartmentName(String departmentName) {
		// Check if department exists
		if (!sdmDepartmentRepository.existsByNameIgnoreCase(departmentName)) {
			throw new SdmEntityNotFoundException("Department not found with name: " + departmentName);
		}
		List<SdmUserEntity> users = sdmUserRepository.findByDepartmentNameIgnoreCase(departmentName);
		return usermapper.toToList(users);
	}

	@Override
	public SdmUserTo assignDepartmentToUser(String username, String departmentName) {
		// Find user
		SdmUserEntity user = sdmUserRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + username));
		// Check if department exists
		// Find department
		SdmDepartmentEntity department = sdmDepartmentRepository.findByNameIgnoreCase(departmentName)
				.orElseThrow(() -> new RuntimeException("Department not found with name: " + departmentName));

		// Assign department and save
		user.setDepartment(department);
		return usermapper.toTo(sdmUserRepository.save(user));
	}

	@Override
	public List<SdmDeviceTo> findDeviceByUser(String username) {
		// Load the user with devices
		SdmUserEntity user = sdmUserRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + username));

		List<SdmDeviceEntity> devices = user.getDevices();

		// Fail if the user has no devices
		if (devices == null || devices.isEmpty()) {
			throw new SdmEntityNotFoundException("No devices found for user: " + username);
		}

		// Convert to DTOs and return
		return devicmapper.toToList(devices);
	}

}
