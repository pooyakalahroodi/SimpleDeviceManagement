package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.mapper.HandOverProtocolMapper;
import com.progiton.trainee.simple.devicemanagement.persistent.model.HandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.services.HandOverProtocolService;
import com.progiton.trainee.simple.devicemanagement.model.requests.HandOverProtocolRequest;
import com.progiton.trainee.simple.devicemanagement.model.to.HandOverProtocolTo;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/handover-protocols")
public class HandOverProtocolController {

    private final HandOverProtocolService service;
    private final HandOverProtocolMapper mapper;

    
    public HandOverProtocolController(HandOverProtocolService service, HandOverProtocolMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    
    @PostMapping
    public ResponseEntity<HandOverProtocolTo> createProtocol(@RequestBody HandOverProtocolRequest request) {
        // Save the entity using service layer
        HandOverProtocolEntity savedEntity = service.saveHandOverProtocol(request);

        // Convert saved entity to a Transfer Object (TO) for response
        HandOverProtocolTo response = mapper.toTo(savedEntity);

        // Return 201 Created with the new protocol
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    


    /**
     *  Get ALL handover protocols for a given device serial number
     */
    @GetMapping("/device/{serialNumber}")
    public ResponseEntity<List<HandOverProtocolTo>> getProtocolsByDeviceSerialNumber(@PathVariable String serialNumber) {
        List<HandOverProtocolEntity> list = service.getByDeviceSerialNumber(serialNumber);
        return ResponseEntity.ok(mapper.toToList(list));
    }

    /**
     *  Get the LATEST handover protocol for a device
     */
    @GetMapping("/device/{serialNumber}/latest")
    public ResponseEntity<HandOverProtocolTo> getLatestProtocol(@PathVariable String serialNumber) {
        List<HandOverProtocolEntity> protocols = service.getByDeviceSerialNumber(serialNumber);
        HandOverProtocolEntity latest = protocols.stream()
                .sorted((a, b) -> b.getHandoverDate().compareTo(a.getHandoverDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No protocols found for device serial: " + serialNumber));
        return ResponseEntity.ok(mapper.toTo(latest));
    }

    /**
     *  Confirm the latest unconfirmed handover protocol for a device
     */
    @PostMapping("/device/{serialNumber}/confirm")
    public ResponseEntity<HandOverProtocolTo> confirmLatestUnconfirmed(@PathVariable String serialNumber) {
        HandOverProtocolEntity confirmed = service.confirmByDeviceSerialNumber(serialNumber);
        return ResponseEntity.ok(mapper.toTo(confirmed));
    }
    
    @GetMapping("/receiver/{username}")
    public ResponseEntity<List<HandOverProtocolTo>> getProtocolsByReceiverUsername(@PathVariable String username) {
        List<HandOverProtocolEntity> list = service.getHandOverProtocolsByReceiverUsername(username);
        return ResponseEntity.ok(mapper.toToList(list));
    }
}
