package com.progiton.trainee.simple.devicemanagement.model;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;

import java.time.Instant;

public interface SdmHandOverProtocol {

	String getDeviceSerialNumber();

	String getReceiverUsername();

	String getPerformedByUsername();

	Instant getHandoverDate();

	String getComments();

	boolean getIsConfirmed();

	Instant getConfirmedAt();

	SdmActionType getActionType();
}
