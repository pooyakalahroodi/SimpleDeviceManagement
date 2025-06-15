package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;


import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/devices")
public class SdmDeviceController {

    private final SdmDeviceService sdmDeviceService;
    private final SdmDeviceMapper sdmDeviceMapper;

//    public DeviceController(DeviceService deviceService, DeviceMapper deviceMapper) {
//        this.deviceService = deviceService;
//        this.deviceMapper = deviceMapper;
//    }
    
    public SdmDeviceController(SdmDeviceService sdmDeviceService, SdmDeviceMapper sdmDeviceMapper) {
        this.sdmDeviceService = sdmDeviceService;
        this.sdmDeviceMapper = sdmDeviceMapper;
    }

    @GetMapping
    public List<SdmDeviceTo> getAllDevices() {
        return sdmDeviceMapper.toToList(sdmDeviceService.getAllDevices());
    }

    @PostMapping
    public ResponseEntity<SdmDeviceTo> saveDevice(@RequestBody SdmDeviceTo dto) {
        System.out.println("💬 Incoming DTO: " + dto); // 🔍 log raw DTO
        
        SdmDeviceEntity sdmDeviceEntity = sdmDeviceMapper.toEntity(dto);
        System.out.println("🛠️ Mapped Device: " + sdmDeviceEntity); // 🔍 log mapped entity

        SdmDeviceEntity saved = sdmDeviceService.saveDevice(sdmDeviceEntity);
        System.out.println("💾 Saved Device: " + saved); // ✅ check saved object

        return ResponseEntity.ok(sdmDeviceMapper.toTo(saved));
    }
    
    // Assigning Device to User
    
    @PutMapping("/assign-device")
    public ResponseEntity<SdmDeviceTo> assignDeviceToUser(@RequestParam String serialNumber,
                                                        @RequestParam String username) {
        SdmDeviceEntity updatedDevice = sdmDeviceService.assignDeviceToUser(serialNumber, username);
        return ResponseEntity.ok(sdmDeviceMapper.toTo(updatedDevice));
    }
    
    @PutMapping("/update-status")
    public ResponseEntity<SdmDeviceTo> updateDeviceStatus(@RequestParam String serialNumber, SdmDeviceStatus newStatus) {
        SdmDeviceEntity updated = sdmDeviceService.updateDeviceStatus(serialNumber, newStatus);
        return ResponseEntity.ok(sdmDeviceMapper.toTo(updated));
    }
}
