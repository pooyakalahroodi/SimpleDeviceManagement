package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.services.DeviceService;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.DeviceRepository;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService{
	
    private final DeviceRepository deviceRepository;

	
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    @Override
    public List<DeviceEntity> getAllDevices() {
        List<DeviceEntity> deviceEntities = deviceRepository.findAll();
        log.info("Fetched {} devices from DB", deviceEntities.size());
        return deviceEntities;
    }

	@Override
	public DeviceEntity getDeviceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeviceEntity saveDevice(DeviceEntity deviceEntity) {
		// TODO Auto-generated method stub
		return deviceRepository.save(deviceEntity);
	}

	@Override
	public void deleteDevice(Long id) {
		// TODO Auto-generated method stub
		
	}

}
