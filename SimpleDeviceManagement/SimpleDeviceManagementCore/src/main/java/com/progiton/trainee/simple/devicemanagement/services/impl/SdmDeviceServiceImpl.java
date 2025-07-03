package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityNotFoundException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SdmDeviceServiceImpl implements SdmDeviceService {

	private final SdmDeviceRepository sdmDeviceRepository;
	private final SdmDeviceMapper mapper;

	public SdmDeviceServiceImpl(SdmDeviceRepository sdmDeviceRepository, SdmDeviceMapper mapper) {
		this.sdmDeviceRepository = sdmDeviceRepository;
		this.mapper = mapper;
	}

	@Override
	public List<SdmDeviceTo> findAllDevices() {
		List<SdmDeviceEntity> devices = sdmDeviceRepository.findAll();
		log.info("Fetched {} devices from DB", devices.size());
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
		SdmDeviceEntity entity = mapper.toEntity(deviceTo);
		SdmDeviceEntity saved = sdmDeviceRepository.save(entity);

		log.info("Saved device with id {}", saved.getId());
		return mapper.toTo(saved);
	}

	@Override
	public SdmDeviceTo updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus) {
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new SdmEntityNotFoundException("Device not found with serial: " + serialNumber));

		device.setStatus(newStatus);
		SdmDeviceEntity saved = sdmDeviceRepository.save(device);

		return mapper.toTo(saved);
	}

}
