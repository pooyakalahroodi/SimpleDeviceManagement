package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceCoreService;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;

@Service
public class SdmDeviceServiceImpl implements SdmDeviceService {

	private final SdmDeviceCoreService sdmDeviceCoreService;

	public SdmDeviceServiceImpl(SdmDeviceCoreService sdmDeviceCoreService) {
		this.sdmDeviceCoreService = sdmDeviceCoreService;
	}

	@Override
	public List<SdmDeviceTo> findAllDevices() {
		return sdmDeviceCoreService.findAllDevices();
	}

	@Override
	public SdmDeviceTo findDeviceBySerialNumber(String serialNumber) {
		return sdmDeviceCoreService.findDeviceBySerialNumber(serialNumber);
	}

	@Override
	public SdmDeviceTo saveDevice(SdmDeviceTo deviceTo) {
		return sdmDeviceCoreService.saveDevice(deviceTo);
	}

	@Override
	public SdmDeviceTo updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus) {
		return sdmDeviceCoreService.updateDeviceStatus(serialNumber, newStatus);
	}

}
