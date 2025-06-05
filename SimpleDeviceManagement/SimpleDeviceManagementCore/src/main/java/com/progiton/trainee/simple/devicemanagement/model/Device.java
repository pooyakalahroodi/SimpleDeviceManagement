package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;

public interface Device {
	
	String getName();
    String getType();
    String getSerialNumber();
    String getManufacturer();
    String getLocation();
    LocalDate getPurchaseDate();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    DeviceStatus getStatus();
    String getAssignedToName();

}
