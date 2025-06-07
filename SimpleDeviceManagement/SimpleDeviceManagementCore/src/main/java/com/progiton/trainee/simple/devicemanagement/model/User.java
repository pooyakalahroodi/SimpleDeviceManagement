package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDateTime;
import java.util.List;

public interface User {

	String getName();
	String getUsername();
	Boolean getEnabled();	
	String getDepartment();
	List<String> getDevices();
	LocalDateTime getCreatedAt();
	LocalDateTime getUpdatedAt();
}
