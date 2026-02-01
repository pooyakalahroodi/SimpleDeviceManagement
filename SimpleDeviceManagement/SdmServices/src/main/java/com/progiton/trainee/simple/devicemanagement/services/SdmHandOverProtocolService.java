package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;

public interface SdmHandOverProtocolService {
	List<SdmHandOverProtocolTo> findAllHandOverProtocols();

	SdmHandOverProtocolTo saveHandOverProtocol(SdmHandOverProtocolTo request);

	List<SdmHandOverProtocolTo> findHandOverProtocolsByReceiverUsername(String username);

	List<SdmHandOverProtocolTo> findByDeviceSerialNumber(String serialNumber);

	SdmHandOverProtocolTo confirmByDeviceSerialNumber(String serialNumber);
//    HandOverProtocolEntity saveHandOverProtocol(HandOverProtoco dto);

	List<SdmHandOverProtocolTo> findHandOverProtocolsByPerformerUsername(String username);

	SdmHandOverProtocolTo findNonConfirmedProtocolsByDeviceSerialNumber(String serialNumber);

}
