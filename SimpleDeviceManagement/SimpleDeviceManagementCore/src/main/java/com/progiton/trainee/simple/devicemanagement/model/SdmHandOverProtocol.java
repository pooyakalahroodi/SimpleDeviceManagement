package com.progiton.trainee.simple.devicemanagement.model;

import java.time.Instant;

public interface SdmHandOverProtocol {
	
	String getDeviceSerialNumber();
	String getReceiverUsername();
	String getPerformedByUsername();
	Instant getHandoverDate();
	String getComments();
	//TODO (LR): Erste Frag: warum Boolean und nicht boolean
	Boolean getIsConfirmed();
	Instant getConfirmedAt();
}
