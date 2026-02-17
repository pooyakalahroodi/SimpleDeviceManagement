package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityNotFoundException;
import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityAlreadyExistsException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceCoreService;

@Service
public class SdmDeviceCoreServiceImpl implements SdmDeviceCoreService {

	private final SdmDeviceRepository sdmDeviceRepository;
	private final SdmDeviceMapper mapper;

	public SdmDeviceCoreServiceImpl(SdmDeviceRepository sdmDeviceRepository, SdmDeviceMapper mapper) {
		this.sdmDeviceRepository = sdmDeviceRepository;
		this.mapper = mapper;
	}

	@Override
	public List<SdmDeviceTo> findAllDevices() {
		List<SdmDeviceEntity> devices = sdmDeviceRepository.findAll();
		return mapper.toToList(devices);
	}

	@Override
	public SdmDeviceTo findDeviceBySerialNumber(String serialNumber) {
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new SdmEntityNotFoundException("Device not found with serial: " + serialNumber));

		return mapper.toTo(device);
	}

	@Override
	public SdmDeviceTo saveDevice(SdmDeviceTo deviceTo) {
		final String serial = deviceTo.getSerialNumber();
		// pre-check purely by serial
		sdmDeviceRepository.findBySerialNumber(serial).ifPresent(existing -> {
			throw new SdmEntityAlreadyExistsException(
					"A device with serialNumber '" + serial + "' already exists.");
		});
		SdmDeviceEntity entity = mapper.toEntity(deviceTo);
		SdmDeviceEntity saved = sdmDeviceRepository.save(entity);

		return mapper.toTo(saved);
	}

	@Override
	public SdmDeviceTo updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus) {
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new SdmEntityNotFoundException("Device not found with serial: " + serialNumber));

		try {
			device.setStatus(newStatus);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Invalid device status: " + newStatus);
		}

		SdmDeviceEntity saved = sdmDeviceRepository.save(device);

		return mapper.toTo(saved);
	}

	@Override
	public boolean existsBySerialNumber(String serialNumber) {
        return sdmDeviceRepository.existsBySerialNumber(serialNumber);
	}

}
