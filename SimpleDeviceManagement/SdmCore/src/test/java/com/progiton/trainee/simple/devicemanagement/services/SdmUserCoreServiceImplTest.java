package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityAlreadyExistsException;
import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityNotFoundException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmUserMapper;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDepartmentRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmUserRepository;
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmUserCoreServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmUserCoreServiceImplTest {

	@Mock
	private SdmUserRepository userRepository;

	@Mock
	private SdmDepartmentRepository departmentRepository;

	@Mock
	private SdmDeviceRepository deviceRepository;

	@Mock
	private SdmUserMapper userMapper;

	@Mock 
	private SdmDeviceMapper deviceMapper;

	@InjectMocks
	private SdmUserCoreServiceImpl userService;

	private SdmUserTo userRequest;
	private SdmUserEntity userEntity;
	private SdmDepartmentEntity departmentEntity;
	private SdmDeviceEntity deviceEntity;
	private SdmDeviceTo deviceTo;
	private SdmUserTo userTo;

	@BeforeEach
	void setUp() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		userRequest = new SdmUserTo();
		userRequest.setUserId(testUserId);
		userRequest.setEmailAddress("testuser@example.com");
		userRequest.setName("Test");
		userRequest.setSurname("User");
		userRequest.setEnabled(true);

		SdmDepartmentTo deptTo = new SdmDepartmentTo();
		deptTo.setName("IT");
		userRequest.setDepartment(deptTo);

		departmentEntity = new SdmDepartmentEntity();
		departmentEntity.setId(1L);
		departmentEntity.setName("IT");

		userEntity = new SdmUserEntity();
		userEntity.setId(1L);
		userEntity.setEmailAddress("testuser@example.com");
		userEntity.setName("Test");
		userEntity.setSurname("User");
		userEntity.setEnabled(true);
		userEntity.setDepartment(departmentEntity);

		deviceEntity = new SdmDeviceEntity();
		deviceEntity.setId(1L);
		deviceEntity.setName("Laptop");
		deviceEntity.setSerialNumber("ABC123");
		deviceEntity.setUser(userEntity);

		userTo = new SdmUserTo();
		userTo.setUserId(testUserId);
		userTo.setEmailAddress("testuser@example.com");

		deviceTo = new SdmDeviceTo();
		deviceTo.setSerialNumber("ABC123");
	}

	@Test
	void createUser_Success() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		when(userRepository.existsByUserId(testUserId)).thenReturn(false);
		when(departmentRepository.findByNameIgnoreCase("IT")).thenReturn(Optional.of(departmentEntity));
		when(userRepository.save(any(SdmUserEntity.class))).thenReturn(userEntity);
		when(userMapper.toTo(userEntity)).thenReturn(userTo);

		SdmUserTo result = userService.createUser(userRequest);

		assertThat(result).isNotNull();
		assertThat(result.getEmailAddress()).isEqualTo("testuser@example.com");

		verify(userRepository).existsByUserId(testUserId);
		verify(departmentRepository).findByNameIgnoreCase("IT");
		verify(userRepository).save(any(SdmUserEntity.class));
	}

	@Test
	void createUser_UserAlreadyExists_ThrowsException() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		when(userRepository.existsByUserId(testUserId)).thenReturn(true);

		assertThrows(SdmEntityAlreadyExistsException.class, () -> userService.createUser(userRequest));

		verify(userRepository).existsByUserId(testUserId);
		verify(departmentRepository, never()).findByNameIgnoreCase(anyString());
	}

	@Test
	void createUser_DepartmentNotFound_ThrowsException() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		when(userRepository.existsByUserId(testUserId)).thenReturn(false);
		when(departmentRepository.findByNameIgnoreCase("IT")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> userService.createUser(userRequest));

		verify(userRepository).existsByUserId(testUserId);
		verify(departmentRepository).findByNameIgnoreCase("IT");
	}

	@Test
	void assignDeviceToUser_Success() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		when(deviceRepository.findBySerialNumber("ABC123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUserId(testUserId)).thenReturn(Optional.of(userEntity));
		when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
		when(deviceMapper.toTo(deviceEntity)).thenReturn(deviceTo);

		SdmDeviceTo result = userService.assignDeviceToUser(testUserId, "ABC123");

		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("ABC123");

		verify(deviceRepository).findBySerialNumber("ABC123");
		verify(userRepository).findByUserId(testUserId);
		verify(deviceRepository).save(deviceEntity);
	}

	@Test
	void assignDeviceToUser_DeviceNotFound_ThrowsException() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		when(deviceRepository.findBySerialNumber("ABC123")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> userService.assignDeviceToUser(testUserId, "ABC123"));

		verify(deviceRepository).findBySerialNumber("ABC123");
		verify(userRepository, never()).findByUserId(any(UUID.class));	}

	@Test
	void assignDeviceToUser_UserNotFound_ThrowsException() {
		UUID testUserId =  UUID.fromString("c2a1297d-7651-4c46-ab34-3e360174891c");
		when(deviceRepository.findBySerialNumber("ABC123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUserId(testUserId)).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> userService.assignDeviceToUser(testUserId, "ABC123"));

		verify(deviceRepository).findBySerialNumber("ABC123");
		verify(userRepository).findByUserId(testUserId);
	}
}
