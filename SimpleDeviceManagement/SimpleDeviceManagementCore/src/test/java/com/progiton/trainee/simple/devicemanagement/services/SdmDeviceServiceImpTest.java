package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityNotFoundException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmDeviceServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmDeviceServiceImplTest {

	@Mock
	private SdmDeviceRepository deviceRepository;

	@Mock
	private SdmDeviceMapper deviceMapper;

	@InjectMocks
	private SdmDeviceServiceImpl deviceService;

	private SdmDeviceEntity deviceEntity;
	private SdmDeviceTo deviceTo;

	@BeforeEach
	void setUp() {
		deviceEntity = new SdmDeviceEntity();
		deviceEntity.setId(1L);
		deviceEntity.setName("Laptop");
		deviceEntity.setSerialNumber("SN123");
		deviceEntity.setStatus(SdmDeviceStatus.ACTIVE);

		deviceTo = new SdmDeviceTo();
		deviceTo.setSerialNumber("SN123");
		deviceTo.setName("Laptop");
		deviceTo.setStatus(SdmDeviceStatus.ACTIVE);
	}

	@Test
	void findAllDevices_ReturnsMultipleDeviceTOs() {
		SdmDeviceEntity secondDevice = new SdmDeviceEntity();
		secondDevice.setId(2L);
		secondDevice.setName("Tablet");
		secondDevice.setSerialNumber("SN999");
		secondDevice.setStatus(SdmDeviceStatus.INACTIVE);

		SdmDeviceTo secondTo = new SdmDeviceTo();
		secondTo.setName("Tablet");
		secondTo.setSerialNumber("SN999");
		secondTo.setStatus(SdmDeviceStatus.INACTIVE);

		// 👈 Important: Put BOTH entities into the list
		List<SdmDeviceEntity> entities = Arrays.asList(deviceEntity, secondDevice);
		List<SdmDeviceTo> tos = Arrays.asList(deviceTo, secondTo);

		when(deviceRepository.findAll()).thenReturn(entities);
		when(deviceMapper.toToList(entities)).thenReturn(tos);

		List<SdmDeviceTo> result = deviceService.findAllDevices();

		assertThat(result).hasSize(2);
		assertThat(result).extracting(SdmDeviceTo::getSerialNumber).containsExactlyInAnyOrder("SN123", "SN999");

		verify(deviceRepository).findAll();
		verify(deviceMapper).toToList(entities);
	}

	@Test
	void findDeviceBySerialNumber_Success() {
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(deviceMapper.toTo(deviceEntity)).thenReturn(deviceTo);

		SdmDeviceTo result = deviceService.findDeviceBySerialNumber("SN123");

		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("SN123");

		verify(deviceRepository).findBySerialNumber("SN123");
		verify(deviceMapper).toTo(deviceEntity);
	}

	@Test
	void findDeviceBySerialNumber_NotFound_ThrowsException() {
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> deviceService.findDeviceBySerialNumber("SN123"));

		verify(deviceRepository).findBySerialNumber("SN123");
		verify(deviceMapper, never()).toTo(any());
	}

	@Test
	void saveDevice_Success() {
		when(deviceMapper.toEntity(deviceTo)).thenReturn(deviceEntity);
		when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
		when(deviceMapper.toTo(deviceEntity)).thenReturn(deviceTo);

		SdmDeviceTo result = deviceService.saveDevice(deviceTo);

		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("SN123");

		verify(deviceMapper).toEntity(deviceTo);
		verify(deviceRepository).save(deviceEntity);
		verify(deviceMapper).toTo(deviceEntity);
	}

	@Test
	void updateDeviceStatus_Success() {
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
		when(deviceMapper.toTo(deviceEntity)).thenReturn(deviceTo);

		SdmDeviceTo result = deviceService.updateDeviceStatus("SN123", SdmDeviceStatus.INACTIVE.name());

		assertThat(result).isNotNull();
		assertThat(result.getSerialNumber()).isEqualTo("SN123");

		verify(deviceRepository).findBySerialNumber("SN123");
		verify(deviceRepository).save(deviceEntity);
		verify(deviceMapper).toTo(deviceEntity);

		// additionally confirm status was updated
		assertThat(deviceEntity.getStatus()).isEqualTo(SdmDeviceStatus.INACTIVE);
	}

	@Test
	void updateDeviceStatus_DeviceNotFound_ThrowsException() {
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class,
				() -> deviceService.updateDeviceStatus("SN123", SdmDeviceStatus.INACTIVE.name()));

		verify(deviceRepository).findBySerialNumber("SN123");
		verify(deviceRepository, never()).save(any());
		verify(deviceMapper, never()).toTo(any());
	}
}
