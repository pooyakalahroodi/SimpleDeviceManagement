package com.progiton.trainee.simple.devicemanagement.services.impl;

import com.progiton.trainee.simple.devicemanagement.model.requests.HandOverProtocolRequest;
import com.progiton.trainee.simple.devicemanagement.model.to.HandOverProtocolTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.HandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.HandOverProtocolRepository;
import com.progiton.trainee.simple.devicemanagement.services.DeviceService;
import com.progiton.trainee.simple.devicemanagement.services.HandOverProtocolService;
import com.progiton.trainee.simple.devicemanagement.services.UserService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HandOverProtocolServiceImpl implements HandOverProtocolService {

    private final HandOverProtocolRepository handOverProtocolRepository;
    private final DeviceService deviceService;
    private final UserService userService;

    public HandOverProtocolServiceImpl(
    		HandOverProtocolRepository handOverProtocolRepository,
            DeviceService deviceService,
            UserService userService) {
        this.handOverProtocolRepository = handOverProtocolRepository;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @Override
    public List<HandOverProtocolEntity> getAllHandOverProtocols() {
        return handOverProtocolRepository.findAll();
    }

    @Override
    public HandOverProtocolEntity getHandOverProtocolById(Long id) {
        return handOverProtocolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Protocol not found with id: " + id));
    }

    @Override
    public HandOverProtocolEntity saveHandOverProtocol(HandOverProtocolRequest request) {
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
        DeviceEntity device = deviceService.getDeviceBySerialNumber(request.getDeviceSerialNumber());
        List<UserEntity> receivers = userService.getUserByUsername(request.getReceiverUsername());
        List<UserEntity> performers = userService.getUserByUsername(request.getPerformedByUsername());

        if (receivers.isEmpty()) {
            throw new IllegalArgumentException("Receiver user not found: " + request.getReceiverUsername());
        }
        if (performers.isEmpty()) {
            throw new IllegalArgumentException("PerformedBy user not found: " + request.getPerformedByUsername());
        }

        UserEntity receiver = receivers.get(0);
        UserEntity performedBy = performers.get(0);

        // Create and populate the entity
        HandOverProtocolEntity entity = new HandOverProtocolEntity();
        entity.setDevice(device);
        entity.setReceiver(receiver);
        entity.setPerformedBy(performedBy);
        entity.setHandoverDate(request.getHandoverDate() != null ? request.getHandoverDate() : LocalDateTime.now());
        entity.setComments(request.getComments());
        entity.setIsConfirmed(request.getIsConfirmed() != null ? request.getIsConfirmed() : false);
        entity.setConfirmedAt(request.getConfirmedAt());

        // Save to database
        return handOverProtocolRepository.save(entity);
    }

    @Override
    public void deleteHandOverProtocol(Long id) {
        if (!handOverProtocolRepository.existsById(id)) {
            throw new RuntimeException("Protocol not found with id: " + id);
        }
        handOverProtocolRepository.deleteById(id);
    }

    @Override
    public HandOverProtocolEntity updateHandOverProtocol(Long id, HandOverProtocolEntity updatedEntity) {
        HandOverProtocolEntity existing = getHandOverProtocolById(id);

        existing.setDevice(updatedEntity.getDevice());
        existing.setReceiver(updatedEntity.getReceiver());
        existing.setPerformedBy(updatedEntity.getPerformedBy());
        existing.setHandoverDate(updatedEntity.getHandoverDate());

        return handOverProtocolRepository.save(existing);
    }

    @Override
    public HandOverProtocolEntity confirmHandOverProtocol(Long id) {
        HandOverProtocolEntity entity = getHandOverProtocolById(id);
        // Assuming you want to add a boolean flag like: entity.setConfirmed(true);
        // entity.setConfirmed(true);  // You’d need this field in your entity
        return handOverProtocolRepository.save(entity);
    }

    @Override
    public List<HandOverProtocolEntity> getHandOverProtocolsByReceiverUsername(String username) {
        return handOverProtocolRepository.findByReceiver_Username(username);
    }

    @Override
    public List<HandOverProtocolEntity> getByDeviceSerialNumber(String serialNumber) {
        return handOverProtocolRepository.findAllByDevice_SerialNumber(serialNumber);
    }

    @Override
    public HandOverProtocolEntity confirmByDeviceSerialNumber(String serialNumber) {
        List<HandOverProtocolEntity> protocols = getByDeviceSerialNumber(serialNumber);
        if (protocols.isEmpty()) {
            throw new RuntimeException("No protocol found for device serial: " + serialNumber);
        }

        HandOverProtocolEntity entity = protocols.get(0);
        // entity.setConfirmed(true);  // If you have such a field
        return handOverProtocolRepository.save(entity);
    }
}
