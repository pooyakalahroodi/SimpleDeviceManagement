package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DeviceService {
	List<DeviceEntity> getAllDevices();
	DeviceEntity getDeviceById(Long id);
    DeviceEntity saveDevice(DeviceEntity deviceEntity);
    void deleteDevice(Long id);
    DeviceEntity getDeviceBySerialNumber(String serialNumber);
    DeviceEntity assignDeviceToUser(String serialNumber, String username);
    DeviceEntity updateDeviceStatus(String serialNumber, DeviceStatus newStatus);

}
