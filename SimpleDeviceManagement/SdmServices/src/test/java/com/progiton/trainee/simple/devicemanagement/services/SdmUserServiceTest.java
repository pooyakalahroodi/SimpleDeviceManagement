package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;

@ExtendWith(MockitoExtension.class)
class SdmUserServiceTest {

	@Mock
	private SdmUserCoreService sdmUserCoreService;

	@InjectMocks
	private SdmUserService sdmUserService;

	@Test
	void findAllUsers_DelegatesToCoreService() {
		List<SdmUserTo> expected = Collections.singletonList(new SdmUserTo());
		when(sdmUserCoreService.findAllUsers()).thenReturn(expected);

		List<SdmUserTo> result = sdmUserService.findAllUsers();

		assertThat(result).isEqualTo(expected);
		verify(sdmUserCoreService).findAllUsers();
	}

	@Test
	void saveUser_DelegatesToCoreService() {
		SdmUserTo user = new SdmUserTo();
		when(sdmUserCoreService.saveUser(user)).thenReturn(user);

		SdmUserTo result = sdmUserService.saveUser(user);

		assertThat(result).isEqualTo(user);
		verify(sdmUserCoreService).saveUser(user);
	}

	@Test
	void createUser_DelegatesToCoreService() {
		SdmUserTo request = new SdmUserTo();
		when(sdmUserCoreService.createUser(request)).thenReturn(request);

		SdmUserTo result = sdmUserService.createUser(request);

		assertThat(result).isEqualTo(request);
		verify(sdmUserCoreService).createUser(request);
	}

	@Test
	void assignDeviceToUser_DelegatesToCoreService() {
		UUID user = UUID.randomUUID();
		SdmDeviceTo expected = new SdmDeviceTo();
		when(sdmUserCoreService.assignDeviceToUser(user, "SN123")).thenReturn(expected);

		SdmDeviceTo result = sdmUserService.assignDeviceToUser(user, "SN123");

		assertThat(result).isEqualTo(expected);
		verify(sdmUserCoreService).assignDeviceToUser(user, "SN123");
	}

	@Test
	void findUserByUsername_DelegatesToCoreService() {
		UUID user = UUID.randomUUID();
		SdmUserTo expected = new SdmUserTo();
		when(sdmUserCoreService.findUserByUserId(user)).thenReturn(expected);

		SdmUserTo result = sdmUserService.findUserByUserId(user);

		assertThat(result).isEqualTo(expected);
		verify(sdmUserCoreService).findUserByUserId(user);
	}

	@Test
	void findUsersByDepartmentName_DelegatesToCoreService() {
		List<SdmUserTo> expected = Collections.singletonList(new SdmUserTo());
		when(sdmUserCoreService.findUsersByDepartmentName("IT")).thenReturn(expected);

		List<SdmUserTo> result = sdmUserService.findUsersByDepartmentName("IT");

		assertThat(result).isEqualTo(expected);
		verify(sdmUserCoreService).findUsersByDepartmentName("IT");
	}

	@Test
	void assignDepartmentToUser_DelegatesToCoreService() {
		UUID user = UUID.randomUUID();
		SdmUserTo expected = new SdmUserTo();
		when(sdmUserCoreService.assignDepartmentToUser(user, "IT")).thenReturn(expected);

		SdmUserTo result = sdmUserService.assignDepartmentToUser(user, "IT");

		assertThat(result).isEqualTo(expected);
		verify(sdmUserCoreService).assignDepartmentToUser(user, "IT");
	}

	@Test
	void findDeviceByUser_DelegatesToCoreService() {
		UUID user = UUID.randomUUID();
		List<SdmDeviceTo> expected = Collections.singletonList(new SdmDeviceTo());
		when(sdmUserCoreService.findDeviceByUser(user)).thenReturn(expected);

		List<SdmDeviceTo> result = sdmUserService.findDeviceByUserID(user);

		assertThat(result).isEqualTo(expected);
		verify(sdmUserCoreService).findDeviceByUser(user);
	}
}
