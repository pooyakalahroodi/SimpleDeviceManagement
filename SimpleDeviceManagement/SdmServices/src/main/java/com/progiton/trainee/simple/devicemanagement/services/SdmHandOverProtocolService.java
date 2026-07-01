package com.progiton.trainee.simple.devicemanagement.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;

@Service
public class SdmHandOverProtocolService {

	private final SdmHandOverProtocolCoreService sdmHandOverProtocolCoreService;

	public SdmHandOverProtocolService(SdmHandOverProtocolCoreService sdmHandOverProtocolCoreService) {
		this.sdmHandOverProtocolCoreService = sdmHandOverProtocolCoreService;
	}

	public List<SdmHandOverProtocolTo> findAllHandOverProtocols() {
		return sdmHandOverProtocolCoreService.findAllHandOverProtocols();
	}

	public SdmHandOverProtocolTo saveHandOverProtocol(SdmHandOverProtocolTo request) {
		return sdmHandOverProtocolCoreService.saveHandOverProtocol(request);
	}

	public List<SdmHandOverProtocolTo> findHandOverProtocolsByReceiverUserId(UUID userId) {
		return sdmHandOverProtocolCoreService.findHandOverProtocolsByReceiverUserId(userId);
	}

	public List<SdmHandOverProtocolTo> findHandOverProtocolsByPerformerUsername(UUID userId) {
		return sdmHandOverProtocolCoreService.findHandOverProtocolsByPerformerUserId(userId);
	}

	public List<SdmHandOverProtocolTo> findByDeviceSerialNumber(String serialNumber) {
		return sdmHandOverProtocolCoreService.findByDeviceSerialNumber(serialNumber);
	}

	public SdmHandOverProtocolTo findNonConfirmedProtocolsByDeviceSerialNumber(String serialNumber) {
		return sdmHandOverProtocolCoreService.findNonConfirmedProtocolsByDeviceSerialNumber(serialNumber);
	}

	public SdmHandOverProtocolTo confirmByDeviceSerialNumber(String serialNumber) {
		return sdmHandOverProtocolCoreService.confirmByDeviceSerialNumber(serialNumber);
	}

}
