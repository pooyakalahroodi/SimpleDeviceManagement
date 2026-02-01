package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolCoreService;
import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;

@Service
public class SdmHandOverProtocolServiceImpl implements SdmHandOverProtocolService {

	private final SdmHandOverProtocolCoreService sdmHandOverProtocolCoreService;

	public  SdmHandOverProtocolServiceImpl(SdmHandOverProtocolCoreService sdmHandOverProtocolCoreService) {
		this.sdmHandOverProtocolCoreService = sdmHandOverProtocolCoreService;
	}

	@Override
	public List<SdmHandOverProtocolTo> findAllHandOverProtocols() {
		return sdmHandOverProtocolCoreService.findAllHandOverProtocols();
	}

	@Override
	public SdmHandOverProtocolTo saveHandOverProtocol(SdmHandOverProtocolTo request) {
		return sdmHandOverProtocolCoreService.saveHandOverProtocol(request);
	}

	@Override
	public List<SdmHandOverProtocolTo> findHandOverProtocolsByReceiverUsername(String username) {
		return sdmHandOverProtocolCoreService.findHandOverProtocolsByReceiverUsername(username);
	}

	@Override
	public List<SdmHandOverProtocolTo> findHandOverProtocolsByPerformerUsername(String username) {
		return sdmHandOverProtocolCoreService.findHandOverProtocolsByPerformerUsername(username);
	}

	@Override
	public List<SdmHandOverProtocolTo> findByDeviceSerialNumber(String serialNumber) {
		return sdmHandOverProtocolCoreService.findByDeviceSerialNumber(serialNumber);
	}

	@Override
	public SdmHandOverProtocolTo findNonConfirmedProtocolsByDeviceSerialNumber(String serialNumber) {
		return sdmHandOverProtocolCoreService.findNonConfirmedProtocolsByDeviceSerialNumber(serialNumber);
	}

	@Override
	public SdmHandOverProtocolTo confirmByDeviceSerialNumber(String serialNumber) {
		return sdmHandOverProtocolCoreService.confirmByDeviceSerialNumber(serialNumber);
	}

}
