package com.progiton.trainee.simple.devicemanagement.model;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;

import java.time.Instant;
import java.util.UUID;

public interface SdmHandOverProtocol {

	String getDeviceSerialNumber();

	UUID getReceiverUserId();

	UUID getPerformedByUserId();

	Instant getHandoverDate();

	String getComments();

	boolean getIsConfirmed();

	Instant getConfirmedAt();

	SdmActionType getActionType();
}
