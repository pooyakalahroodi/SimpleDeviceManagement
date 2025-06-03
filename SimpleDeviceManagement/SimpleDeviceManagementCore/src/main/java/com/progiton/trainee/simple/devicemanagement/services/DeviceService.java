package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.DeviceEntity;
import java.util.List;

public interface DeviceService {
	List<DeviceEntity> getAllDevices();
	DeviceEntity getDeviceById(Long id);
    DeviceEntity saveDevice(DeviceEntity deviceEntity);
    void deleteDevice(Long id);
}
