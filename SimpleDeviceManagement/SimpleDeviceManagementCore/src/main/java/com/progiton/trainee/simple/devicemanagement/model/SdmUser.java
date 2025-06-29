package com.progiton.trainee.simple.devicemanagement.model;

import java.util.List;

public interface SdmUser<D extends SdmDevice> {

	String getName();
	String getUsername();
	Boolean getEnabled();	
	SdmDepartment getDepartment();
	List<D> getDevices();

}
