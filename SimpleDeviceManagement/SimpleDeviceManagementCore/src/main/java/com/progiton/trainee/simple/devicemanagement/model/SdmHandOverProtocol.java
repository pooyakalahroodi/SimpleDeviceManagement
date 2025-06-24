package com.progiton.trainee.simple.devicemanagement.model;

import java.time.Instant;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;

public interface SdmHandOverProtocol {
	
	String getDeviceSerialNumber();
	String getReceiverUsername();
	String getPerformedByUsername();
	Instant getHandoverDate();
	String getComments();
	boolean getIsConfirmed();
	Instant getConfirmedAt();
	SdmActionType getSdmActionType();
}
