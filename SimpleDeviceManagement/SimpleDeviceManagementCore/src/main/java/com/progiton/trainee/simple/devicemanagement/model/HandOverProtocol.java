package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDateTime;

public interface HandOverProtocol {
	
	String getDeviceSerialNumber();
	String getReceiverUsername();
	String getPerformedByUsername();
	LocalDateTime getHandoverDate();
	String getComments();
	Boolean getIsConfirmed();
	LocalDateTime getConfirmedAt();
}
