package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.services.DeviceService;
import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.DeviceTo;

import lombok.RequiredArgsConstructor;

import com.progiton.trainee.simple.devicemanagement.mapper.DeviceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;

//    public DeviceController(DeviceService deviceService, DeviceMapper deviceMapper) {
//        this.deviceService = deviceService;
//        this.deviceMapper = deviceMapper;
//    }
    
    public DeviceController(DeviceService deviceService, DeviceMapper deviceMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
    }

    @GetMapping
    public List<DeviceTo> getAllDevices() {
        return deviceMapper.toToList(deviceService.getAllDevices());
    }

    @PostMapping
    public ResponseEntity<DeviceTo> saveDevice(@RequestBody DeviceTo dto) {
        System.out.println("💬 Incoming DTO: " + dto); // 🔍 log raw DTO
        
        DeviceEntity deviceEntity = deviceMapper.toEntity(dto);
        System.out.println("🛠️ Mapped Device: " + deviceEntity); // 🔍 log mapped entity

        DeviceEntity saved = deviceService.saveDevice(deviceEntity);
        System.out.println("💾 Saved Device: " + saved); // ✅ check saved object

        return ResponseEntity.ok(deviceMapper.toTo(saved));
    }
    
    // Assigning Device to User
    
    @PutMapping("/assign-device")
    public ResponseEntity<DeviceTo> assignDeviceToUser(@RequestParam String serialNumber,
                                                        @RequestParam String username) {
        DeviceEntity updatedDevice = deviceService.assignDeviceToUser(serialNumber, username);
        return ResponseEntity.ok(deviceMapper.toTo(updatedDevice));
    }
    
    @PutMapping("/update-status")
    public ResponseEntity<DeviceTo> updateDeviceStatus(@RequestParam String serialNumber, DeviceStatus newStatus) {
        DeviceEntity updated = deviceService.updateDeviceStatus(serialNumber, newStatus);
        return ResponseEntity.ok(deviceMapper.toTo(updated));
    }
}
