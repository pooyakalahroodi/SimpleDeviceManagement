package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;
import java.util.Optional;

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
        Optional<DeviceEntity> deviceOpt = deviceRepository.findById(id);
        if (deviceOpt.isEmpty()) {
            log.warn("Device with id {} not found", id);
            return null; // or throw exception
        }
        return deviceOpt.get();
    }

    @Override
    public DeviceEntity saveDevice(DeviceEntity deviceEntity) {
        DeviceEntity saved = deviceRepository.save(deviceEntity);
        log.info("Saved device with id {}", saved.getId());
        return saved;
    }

    @Override
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            log.warn("Attempted to delete non-existing device with id {}", id);
            return;
        }
        deviceRepository.deleteById(id);
        log.info("Deleted device with id {}", id);
    }

}
