package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmDeviceServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmDeviceServiceImplTest {

	@Mock
	private SdmDeviceCoreService sdmDeviceCoreService;

	@InjectMocks
	private SdmDeviceServiceImpl sdmDeviceService;

	@Test
	void findAllDevices_DelegatesToCoreService() {
		List<SdmDeviceTo> expected = Collections.singletonList(new SdmDeviceTo());
		when(sdmDeviceCoreService.findAllDevices()).thenReturn(expected);

		List<SdmDeviceTo> result = sdmDeviceService.findAllDevices();

		assertThat(result).isEqualTo(expected);
		verify(sdmDeviceCoreService).findAllDevices();
	}

	@Test
	void findDeviceBySerialNumber_DelegatesToCoreService() {
		SdmDeviceTo expected = new SdmDeviceTo();
		when(sdmDeviceCoreService.findDeviceBySerialNumber("SN123")).thenReturn(expected);

		SdmDeviceTo result = sdmDeviceService.findDeviceBySerialNumber("SN123");

		assertThat(result).isEqualTo(expected);
		verify(sdmDeviceCoreService).findDeviceBySerialNumber("SN123");
	}

	@Test
	void saveDevice_DelegatesToCoreService() {
		SdmDeviceTo device = new SdmDeviceTo();
		when(sdmDeviceCoreService.saveDevice(device)).thenReturn(device);

		SdmDeviceTo result = sdmDeviceService.saveDevice(device);

		assertThat(result).isEqualTo(device);
		verify(sdmDeviceCoreService).saveDevice(device);
	}

	@Test
	void updateDeviceStatus_DelegatesToCoreService() {
		SdmDeviceTo expected = new SdmDeviceTo();
		when(sdmDeviceCoreService.updateDeviceStatus("SN123", SdmDeviceStatus.ACTIVE)).thenReturn(expected);

		SdmDeviceTo result = sdmDeviceService.updateDeviceStatus("SN123", SdmDeviceStatus.ACTIVE);

		assertThat(result).isEqualTo(expected);
		verify(sdmDeviceCoreService).updateDeviceStatus("SN123", SdmDeviceStatus.ACTIVE);
	}
}
