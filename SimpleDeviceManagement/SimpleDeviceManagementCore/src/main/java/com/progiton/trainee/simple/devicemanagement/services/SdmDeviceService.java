package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface SdmDeviceService {
	List<SdmDeviceEntity> getAllDevices();
	SdmDeviceEntity getDeviceById(Long id);
    SdmDeviceEntity saveDevice(SdmDeviceEntity sdmDeviceEntity);
    void deleteDevice(Long id);
    SdmDeviceEntity getDeviceBySerialNumber(String serialNumber);
    SdmDeviceEntity assignDeviceToUser(String serialNumber, String username);
    SdmDeviceEntity updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus);

}
