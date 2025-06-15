package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmUserRepository;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SdmDeviceServiceImpl implements SdmDeviceService{
	
    private final SdmDeviceRepository sdmDeviceRepository;
    private final SdmUserRepository sdmUserRepository;

	
    public SdmDeviceServiceImpl(SdmDeviceRepository sdmDeviceRepository, SdmUserRepository sdmUserRepository) {
        this.sdmDeviceRepository = sdmDeviceRepository;
		this.sdmUserRepository = sdmUserRepository;
    }


    @Override
    public List<SdmDeviceEntity> getAllDevices() {
        List<SdmDeviceEntity> sdmDeviceEntities = sdmDeviceRepository.findAll();
        log.info("Fetched {} devices from DB", sdmDeviceEntities.size());
        return sdmDeviceEntities;
    }

    @Override
    public SdmDeviceEntity getDeviceById(Long id) {
        Optional<SdmDeviceEntity> deviceOpt = sdmDeviceRepository.findById(id);
        if (deviceOpt.isEmpty()) {
            log.warn("Device with id {} not found", id);
            return null; // or throw exception
        }
        return deviceOpt.get();
    }

    @Override
    public SdmDeviceEntity saveDevice(SdmDeviceEntity sdmDeviceEntity) {
        SdmDeviceEntity saved = sdmDeviceRepository.save(sdmDeviceEntity);
        log.info("Saved device with id {}", saved.getId());
        return saved;
    }

    @Override
    public void deleteDevice(Long id) {
        if (!sdmDeviceRepository.existsById(id)) {
            log.warn("Attempted to delete non-existing device with id {}", id);
            return;
        }
        sdmDeviceRepository.deleteById(id);
        log.info("Deleted device with id {}", id);
    }


	@Override
	public SdmDeviceEntity assignDeviceToUser(String serialNumber, String username) {
		List<SdmUserEntity> users = sdmUserRepository.findByUsernameStartingWithIgnoreCase(username);
		SdmUserEntity user = users.get(0); // Use first one

		    SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber);

		    device.setAssignedTo(user);
		    return sdmDeviceRepository.save(device);
	}


	@Override
	public SdmDeviceEntity updateDeviceStatus(String serialNumber, SdmDeviceStatus newStatus) {
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(serialNumber);

		    device.setStatus(newStatus);
		    return sdmDeviceRepository.save(device);
	}



	@Override
	public SdmDeviceEntity getDeviceBySerialNumber(String serialNumber) {
		return sdmDeviceRepository.findBySerialNumber(serialNumber);
	}
	

}
