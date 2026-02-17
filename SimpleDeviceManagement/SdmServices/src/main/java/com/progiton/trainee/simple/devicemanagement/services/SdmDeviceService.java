package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;

@Service
public class SdmDeviceService {

	private final SdmDeviceCoreService sdmDeviceCoreService;

	public SdmDeviceService(SdmDeviceCoreService sdmDeviceCoreService) {
		this.sdmDeviceCoreService = sdmDeviceCoreService;
	}

	public List<SdmDeviceTo> findAllDevices() {
		return sdmDeviceCoreService.findAllDevices();
	}

	public SdmDeviceTo findDeviceBySerialNumber(String serialNumber) {
		return sdmDeviceCoreService.findDeviceBySerialNumber(serialNumber);
	}

	public SdmDeviceTo saveDevice(SdmDeviceTo deviceTo) {
		return sdmDeviceCoreService.saveDevice(deviceTo);
	}

	public SdmDeviceTo updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus) {
		return sdmDeviceCoreService.updateDeviceStatus(serialNumber, newStatus);
	}

	public boolean existsBySerialNumber(String serialNumber) {
		return sdmDeviceCoreService.existsBySerialNumber(serialNumber);
	}

}
