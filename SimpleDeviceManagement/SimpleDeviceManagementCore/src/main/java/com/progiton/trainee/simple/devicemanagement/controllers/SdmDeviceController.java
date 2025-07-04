package com.progiton.trainee.simple.devicemanagement.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progiton.trainee.simple.devicemanagement.mapper.SdmDeviceMapper;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;

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
		return sdmDeviceService.findAllDevices();
	}

	@PostMapping
	public ResponseEntity<SdmDeviceTo> saveDevice(@RequestBody SdmDeviceTo device) {
		System.out.println("💬 Incoming DTO: " + device); // 🔍 log raw DTO

		SdmDeviceTo saved = sdmDeviceService.saveDevice(device);
		System.out.println("💾 Saved Device: " + saved); // ✅ check saved object

		return ResponseEntity.ok(device);
	}

	// Assigning Device to User
//
//	@PutMapping("/assign-device")
//	public ResponseEntity<SdmDeviceTo> assignDeviceToUser(@RequestParam String serialNumber,
//			@RequestParam String username) {
//		SdmDeviceTo updatedDevice = sdmDeviceService.assignDeviceToUser(serialNumber, username);
//		return ResponseEntity.ok(sdmDeviceMapper.toTo(updatedDevice));
//	}

	@PutMapping("/update-status")
	public ResponseEntity<SdmDeviceTo> updateDeviceStatus(@RequestParam String serialNumber, String newStatus) {
		SdmDeviceTo updated = sdmDeviceService.updateDeviceStatus(serialNumber, newStatus);
		return ResponseEntity.ok(updated);
	}
}
