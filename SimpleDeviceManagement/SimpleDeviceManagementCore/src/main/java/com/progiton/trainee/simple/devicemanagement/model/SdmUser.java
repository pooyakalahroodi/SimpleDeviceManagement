package com.progiton.trainee.simple.devicemanagement.model;

import java.util.List;

public interface SdmUser {

	String getName();
	String getUsername();
	Boolean getEnabled();	
	String getDepartment();
	List<String> getDevices();

}
