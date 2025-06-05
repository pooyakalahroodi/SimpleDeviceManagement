package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface Device {
	
	String getName();
    String getType();
    String getSerialNumber();
    String getManufacturer();
    String getLocation();
    LocalDate getPurchaseDate();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    String getStatus();
    String getAssignedToUsername();



}
