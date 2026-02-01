package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;

public interface SdmDeviceCoreService {
	List<SdmDeviceTo> findAllDevices();

	SdmDeviceTo saveDevice(SdmDeviceTo device);

	SdmDeviceTo findDeviceBySerialNumber(String serialNumber);

	SdmDeviceTo updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus);

}
