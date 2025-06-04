package com.progiton.trainee.simple.devicemanagement.controllers;

import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.services.DeviceService;
import com.progiton.trainee.simple.devicemanagement.model.to.DeviceTo;

import lombok.RequiredArgsConstructor;

import com.progiton.trainee.simple.devicemanagement.mapper.DeviceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

	@Autowired
    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;

//    public DeviceController(DeviceService deviceService, DeviceMapper deviceMapper) {
//        this.deviceService = deviceService;
//        this.deviceMapper = deviceMapper;
//    }

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
}
