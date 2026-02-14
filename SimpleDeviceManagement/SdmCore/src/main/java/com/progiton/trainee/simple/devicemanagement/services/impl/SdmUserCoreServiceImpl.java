package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.progiton.trainee.simple.devicemanagement.services.SdmUserCoreService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SdmUserCoreServiceImpl implements SdmUserCoreService {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SdmUserCoreServiceImpl.class);

	private final SdmUserRepository sdmUserRepository;
	private final SdmDepartmentRepository sdmDepartmentRepository;
	private final SdmDeviceRepository sdmDeviceRepository;
	private final SdmUserMapper usermapper;
	private final SdmDeviceMapper devicmapper;

	public SdmUserCoreServiceImpl(SdmUserRepository sdmUserRepository, SdmDepartmentRepository sdmDepartmentRepository,
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
		log.debug("Checking username: {}", request.getEmailAddress());
		// Check if user already exists
		if (sdmUserRepository.existsByUserId(request.getUserId())) {
			throw new SdmEntityAlreadyExistsException(
					"User with username " + request.getEmailAddress() + " already exists");
		}

		// Create new user
		SdmUserEntity user = new SdmUserEntity();
		user.setUserId(UUID.randomUUID());
		user.setEmailAddress(request.getEmailAddress());
		user.setName(request.getName());
		user.setSurname(request.getSurname());
		user.setEnabled(request.getEnabled() != null ? request.getEnabled() : true);

		// Set department if provided
		if (request.getDepartment() != null) {
			SdmDepartmentEntity department = sdmDepartmentRepository
					.findByNameIgnoreCase(request.getDepartment().getName())
					.orElseThrow(() -> new SdmEntityNotFoundException(
							"Department not found with name: " + request.getDepartment().getName()));
			user.setDepartment(department);
		}

		try {
			// Save entity
			SdmUserEntity savedUser = sdmUserRepository.save(user);
			// Convert to DTO
			return usermapper.toTo(savedUser);
		} catch (DataIntegrityViolationException ex) {
			// This catches race conditions or any other unique constraint violations
			throw new SdmEntityAlreadyExistsException("User with username " + request.getEmailAddress() + " already exists",
					ex);
		}
	}

	@Override
	public SdmDeviceTo assignDeviceToUser(UUID userId, String serialNumber) {
		// Fetch the device
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new SdmEntityNotFoundException("Device not found with serial: " + serialNumber));
		// Fetch the user
		SdmUserEntity user = sdmUserRepository.findByUserId(userId)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + userId));
		// Assign the user to the device
		device.setUser(user);
		// Save the updated device
		SdmDeviceEntity saved = sdmDeviceRepository.save(device);
		// Convert to DTO and return
		return devicmapper.toTo(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public SdmUserTo findUserByEmailAddress(@NotBlank @Email String emailAddress) {
		log.debug("Suche Benutzer mit E-Mail: {}", emailAddress);
		SdmUserEntity user = sdmUserRepository.findUserByEmailAddress(emailAddress)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with Email Address: " + emailAddress));
		return usermapper.toTo(user);
	}

	@Override
	public SdmUserTo findUserByUserId(UUID userId) {
		SdmUserEntity user = sdmUserRepository.findByUserId(userId)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + userId));
		return usermapper.toTo(user);
	}

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
	public SdmUserTo assignDepartmentToUser(UUID userId, String departmentName) {
		// Find user
		SdmUserEntity user = sdmUserRepository.findByUserId(userId)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + userId));
		// Find department
		SdmDepartmentEntity department = sdmDepartmentRepository.findByNameIgnoreCase(departmentName)
				.orElseThrow(() -> new SdmEntityNotFoundException("Department not found with name: " + departmentName));

		// Assign department and save
		user.setDepartment(department);
		return usermapper.toTo(sdmUserRepository.save(user));
	}

	@Override
	public List<SdmDeviceTo> findDeviceByUser(UUID userId) {
		// Load the user with devices
		SdmUserEntity user = sdmUserRepository.findByUserId(userId)
				.orElseThrow(() -> new SdmEntityNotFoundException("User not found with username: " + userId));

		List<SdmDeviceEntity> devices = user.getDevices();

		// Fail if the user has no devices
		if (devices == null || devices.isEmpty()) {
			throw new SdmEntityNotFoundException("No devices found for user: " + userId);
		}

		// Convert to DTOs and return
		return devicmapper.toToList(devices);
	}

}
