package com.progiton.trainee.simple.devicemanagement.model;

import java.time.LocalDateTime;

public interface HandOverProtocol {
	
	String getDeviceSerialNumber();
	String getReceiverUsername();
	String getPerformedByUsername();
	LocalDateTime getHandoverDate();
	String getComments();
	//TODO (LR): Erste Frag: warum Boolean und nicht boolean
	Boolean getIsConfirmed();
	LocalDateTime getConfirmedAt();
}
