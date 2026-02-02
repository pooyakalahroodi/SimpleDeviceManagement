package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmUserServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmUserServiceImplTest {

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
	private SdmUserServiceImpl userService;

	private SdmUserTo userRequest;
	private SdmUserEntity userEntity;
	private SdmDepartmentEntity departmentEntity;
	private SdmDeviceEntity deviceEntity;
	private SdmDeviceTo deviceTo;
	private SdmUserTo userTo;

	@BeforeEach
	void setUp() {
		userRequest = new SdmUserTo();
		userRequest.setUsername("testuser");
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
		userEntity.setUsername("testuser");
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
		userTo.setUsername("testuser");

		deviceTo = new SdmDeviceTo();
		deviceTo.setSerialNumber("ABC123");
	}

	@Test
	void createUser_Success() {
		when(userRepository.existsByUsername("testuser")).thenReturn(false);
		when(departmentRepository.findByNameIgnoreCase("IT")).thenReturn(Optional.of(departmentEntity));
		when(userRepository.save(any(SdmUserEntity.class))).thenReturn(userEntity);
		when(userMapper.toTo(userEntity)).thenReturn(userTo);

		SdmUserTo result = userService.createUser(userRequest);

		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo("testuser");

		verify(userRepository).existsByUsername("testuser");
		verify(departmentRepository).findByNameIgnoreCase("IT");
		verify(userRepository).save(any(SdmUserEntity.class));
	}

	@Test
	void createUser_UserAlreadyExists_ThrowsException() {
		when(userRepository.existsByUsername("testuser")).thenReturn(true);

		assertThrows(SdmEntityAlreadyExistsException.class, () -> userService.createUser(userRequest));

		verify(userRepository).existsByUsername("testuser");
		verify(departmentRepository, never()).findByNameIgnoreCase(anyString());
	}

	@Test
	void createUser_DepartmentNotFound_ThrowsException() {
		when(userRepository.existsByUsername("testuser")).thenReturn(false);
		when(departmentRepository.findByNameIgnoreCase("IT")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> userService.createUser(userRequest));

		verify(userRepository).existsByUsername("testuser");
		verify(departmentRepository).findByNameIgnoreCase("IT");
	}

	@Test
	void assignDeviceToUser_Success() {
		when(deviceRepository.findBySerialNumber("ABC123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Optional.of(userEntity));
		when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
		when(deviceMapper.toTo(deviceEntity)).thenReturn(deviceTo);

		SdmDeviceTo result = userService.assignDeviceToUser("testuser", "ABC123");

		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("ABC123");

		verify(deviceRepository).findBySerialNumber("ABC123");
		verify(userRepository).findByUsernameIgnoreCase("testuser");
		verify(deviceRepository).save(deviceEntity);
	}

	@Test
	void assignDeviceToUser_DeviceNotFound_ThrowsException() {
		when(deviceRepository.findBySerialNumber("ABC123")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> userService.assignDeviceToUser("testuser", "ABC123"));

		verify(deviceRepository).findBySerialNumber("ABC123");
		verify(userRepository, never()).findByUsernameIgnoreCase(anyString());
	}

	@Test
	void assignDeviceToUser_UserNotFound_ThrowsException() {
		when(deviceRepository.findBySerialNumber("ABC123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> userService.assignDeviceToUser("testuser", "ABC123"));

		verify(deviceRepository).findBySerialNumber("ABC123");
		verify(userRepository).findByUsernameIgnoreCase("testuser");
	}
}
