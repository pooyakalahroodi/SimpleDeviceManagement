package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.mapper.SdmHandOverProtocolMapper;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;
import com.progiton.trainee.simple.devicemanagement.model.requests.SdmHandOverProtocolRequest;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/handover-protocols")
public class SdmHandOverProtocolController {

    private final SdmHandOverProtocolService service;
    private final SdmHandOverProtocolMapper mapper;

    
    public SdmHandOverProtocolController(SdmHandOverProtocolService service, SdmHandOverProtocolMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    
    @PostMapping
    public ResponseEntity<SdmHandOverProtocolTo> createProtocol(@RequestBody SdmHandOverProtocolRequest request) {
        // Save the entity using service layer
        SdmHandOverProtocolEntity savedEntity = service.saveHandOverProtocol(request);

        // Convert saved entity to a Transfer Object (TO) for response
        SdmHandOverProtocolTo response = mapper.toTo(savedEntity);

        // Return 201 Created with the new protocol
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    


    /**
     *  Get ALL handover protocols for a given device serial number
     */
    @GetMapping("/device/{serialNumber}")
    public ResponseEntity<List<SdmHandOverProtocolTo>> getProtocolsByDeviceSerialNumber(@PathVariable String serialNumber) {
        List<SdmHandOverProtocolEntity> list = service.getByDeviceSerialNumber(serialNumber);
        return ResponseEntity.ok(mapper.toToList(list));
    }

    /**
     *  Get the LATEST handover protocol for a device
     */
    @GetMapping("/device/{serialNumber}/latest")
    public ResponseEntity<SdmHandOverProtocolTo> getLatestProtocol(@PathVariable String serialNumber) {
        List<SdmHandOverProtocolEntity> protocols = service.getByDeviceSerialNumber(serialNumber);
        SdmHandOverProtocolEntity latest = protocols.stream()
                .sorted((a, b) -> b.getHandoverDate().compareTo(a.getHandoverDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No protocols found for device serial: " + serialNumber));
        return ResponseEntity.ok(mapper.toTo(latest));
    }

    /**
     *  Confirm the latest unconfirmed handover protocol for a device
     */
    @PostMapping("/device/{serialNumber}/confirm")
    public ResponseEntity<SdmHandOverProtocolTo> confirmLatestUnconfirmed(@PathVariable String serialNumber) {
        SdmHandOverProtocolEntity confirmed = service.confirmByDeviceSerialNumber(serialNumber);
        return ResponseEntity.ok(mapper.toTo(confirmed));
    }
    
    @GetMapping("/receiver/{username}")
    public ResponseEntity<List<SdmHandOverProtocolTo>> getProtocolsByReceiverUsername(@PathVariable String username) {
        List<SdmHandOverProtocolEntity> list = service.getHandOverProtocolsByReceiverUsername(username);
        return ResponseEntity.ok(mapper.toToList(list));
    }
}
