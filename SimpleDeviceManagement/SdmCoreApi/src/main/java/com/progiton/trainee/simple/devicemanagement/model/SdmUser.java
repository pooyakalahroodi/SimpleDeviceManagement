package com.progiton.trainee.simple.devicemanagement.model;

import java.util.List;
import java.util.UUID;

public interface SdmUser<D extends SdmDevice> {

	UUID getUserId();
	String getEmailAddress();
	String getName();
	String getSurname();
	Boolean getEnabled();	
	SdmDepartment getDepartment();
	List<D> getDevices();


}
