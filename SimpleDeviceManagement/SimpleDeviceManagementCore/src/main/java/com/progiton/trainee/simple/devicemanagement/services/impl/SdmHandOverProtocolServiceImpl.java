package com.progiton.trainee.simple.devicemanagement.services.impl;

import com.progiton.trainee.simple.devicemanagement.model.requests.SdmHandOverProtocolRequest;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmHandOverProtocolRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;
import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;
import com.progiton.trainee.simple.devicemanagement.services.SdmUserService;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SdmHandOverProtocolServiceImpl implements SdmHandOverProtocolService {

    private final SdmHandOverProtocolRepository sdmHandOverProtocolRepository;
    private final SdmDeviceService sdmDeviceService;
    private final SdmUserService sdmUserService;

    public SdmHandOverProtocolServiceImpl(
    		SdmHandOverProtocolRepository sdmHandOverProtocolRepository,
            SdmDeviceService sdmDeviceService,
            SdmUserService sdmUserService) {
        this.sdmHandOverProtocolRepository = sdmHandOverProtocolRepository;
        this.sdmDeviceService = sdmDeviceService;
        this.sdmUserService = sdmUserService;
    }

    @Override
    public List<SdmHandOverProtocolEntity> getAllHandOverProtocols() {
        return sdmHandOverProtocolRepository.findAll();
    }

    @Override
    public SdmHandOverProtocolEntity getHandOverProtocolById(Long id) {
        return sdmHandOverProtocolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Protocol not found with id: " + id));
    }

    @Override
    public SdmHandOverProtocolEntity saveHandOverProtocol(SdmHandOverProtocolRequest request) {
        // Validate required fields
        if (request.getDeviceSerialNumber() == null || request.getDeviceSerialNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Device serial number is required");
        }
        if (request.getReceiverUsername() == null || request.getReceiverUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver username is required");
        }
        if (request.getPerformedByUsername() == null || request.getPerformedByUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Performed by username is required");
        }

        // Lookup device and users
        SdmDeviceEntity device = sdmDeviceService.getDeviceBySerialNumber(request.getDeviceSerialNumber());
        List<SdmUserEntity> receivers = sdmUserService.getUserByUsername(request.getReceiverUsername());
        List<SdmUserEntity> performers = sdmUserService.getUserByUsername(request.getPerformedByUsername());

        if (receivers.isEmpty()) {
            throw new IllegalArgumentException("Receiver user not found: " + request.getReceiverUsername());
        }
        if (performers.isEmpty()) {
            throw new IllegalArgumentException("PerformedBy user not found: " + request.getPerformedByUsername());
        }

        SdmUserEntity receiver = receivers.get(0);
        SdmUserEntity performedBy = performers.get(0);

        // Create and populate the entity
        SdmHandOverProtocolEntity entity = new SdmHandOverProtocolEntity();
        entity.setDevice(device);
        entity.setReceiver(receiver);
        entity.setPerformedBy(performedBy);
        entity.setHandoverDate(request.getHandoverDate() != null ? request.getHandoverDate() : Instant.now());
        entity.setComments(request.getComments());
        entity.setIsConfirmed(request.getIsConfirmed() != null ? request.getIsConfirmed() : false);
        entity.setConfirmedAt(request.getConfirmedAt());

        // Save to database
        return sdmHandOverProtocolRepository.save(entity);
    }

    @Override
    public void deleteHandOverProtocol(Long id) {
        if (!sdmHandOverProtocolRepository.existsById(id)) {
            throw new RuntimeException("Protocol not found with id: " + id);
        }
        sdmHandOverProtocolRepository.deleteById(id);
    }

    @Override
    public SdmHandOverProtocolEntity updateHandOverProtocol(Long id, SdmHandOverProtocolEntity updatedEntity) {
        SdmHandOverProtocolEntity existing = getHandOverProtocolById(id);

        existing.setDevice(updatedEntity.getDevice());
        existing.setReceiver(updatedEntity.getReceiver());
        existing.setPerformedBy(updatedEntity.getPerformedBy());
        existing.setHandoverDate(updatedEntity.getHandoverDate());

        return sdmHandOverProtocolRepository.save(existing);
    }

    @Override
    public SdmHandOverProtocolEntity confirmHandOverProtocol(Long id) {
        SdmHandOverProtocolEntity entity = getHandOverProtocolById(id);
        // Assuming you want to add a boolean flag like: entity.setConfirmed(true);
        // entity.setConfirmed(true);  // You’d need this field in your entity
        return sdmHandOverProtocolRepository.save(entity);
    }

    @Override
    public List<SdmHandOverProtocolEntity> getHandOverProtocolsByReceiverUsername(String username) {
        return sdmHandOverProtocolRepository.findByReceiver_Username(username);
    }

    @Override
    public List<SdmHandOverProtocolEntity> getByDeviceSerialNumber(String serialNumber) {
        return sdmHandOverProtocolRepository.findAllByDevice_SerialNumber(serialNumber);
    }

    @Override
    public SdmHandOverProtocolEntity confirmByDeviceSerialNumber(String serialNumber) {
        List<SdmHandOverProtocolEntity> protocols = getByDeviceSerialNumber(serialNumber);
        if (protocols.isEmpty()) {
            throw new RuntimeException("No protocol found for device serial: " + serialNumber);
        }

        SdmHandOverProtocolEntity entity = protocols.get(0);
        // entity.setConfirmed(true);  // If you have such a field
        return sdmHandOverProtocolRepository.save(entity);
    }
}
