package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;

public interface SdmHandOverProtocolCoreService {
	List<SdmHandOverProtocolTo> findAllHandOverProtocols();

	SdmHandOverProtocolTo saveHandOverProtocol(SdmHandOverProtocolTo request);

	List<SdmHandOverProtocolTo> findHandOverProtocolsByReceiverUserId(UUID userId);

	List<SdmHandOverProtocolTo> findByDeviceSerialNumber(String serialNumber);

	SdmHandOverProtocolTo confirmByDeviceSerialNumber(String serialNumber);

    List<SdmHandOverProtocolTo> findHandOverProtocolsByPerformerUserId(UUID userId);

	SdmHandOverProtocolTo findNonConfirmedProtocolsByDeviceSerialNumber(String serialNumber);

}
