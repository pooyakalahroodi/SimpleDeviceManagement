package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDate;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;


public interface SdmDevice {
	
	String getName();
    String getType();
    String getSerialNumber();
    String getManufacturer();
    String getLocation();
    LocalDate getPurchaseDate();
    SdmDeviceStatus getStatus();
}
