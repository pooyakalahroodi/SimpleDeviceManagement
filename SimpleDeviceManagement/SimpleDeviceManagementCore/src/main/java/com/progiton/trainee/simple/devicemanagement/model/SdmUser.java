package com.progiton.trainee.simple.devicemanagement.model;

import java.util.List;

public interface SdmUser<D extends SdmDevice> {

	String getUsername();
	String getName();
	String getSurname();
	Boolean getEnabled();	
	SdmDepartment getDepartment();
	List<D> getDevices();


}
