package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;

@Service
public interface SdmDeviceService {
	List<SdmDeviceTo> findAllDevices();

	SdmDeviceTo saveDevice(SdmDeviceTo device);

	SdmDeviceTo findDeviceBySerialNumber(String serialNumber);

	SdmDeviceTo updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus);

}
