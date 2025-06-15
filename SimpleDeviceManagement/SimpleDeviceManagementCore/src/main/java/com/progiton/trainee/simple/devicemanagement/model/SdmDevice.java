package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDate;


public interface SdmDevice {
	
	String getName();
    String getType();
    String getSerialNumber();
    String getManufacturer();
    String getLocation();
    LocalDate getPurchaseDate();
    String getStatus();
    String getAssignedToUsername();



}
