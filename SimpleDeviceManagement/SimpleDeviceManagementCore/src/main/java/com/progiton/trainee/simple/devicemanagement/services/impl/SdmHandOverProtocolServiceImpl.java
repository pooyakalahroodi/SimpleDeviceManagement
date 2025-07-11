package com.progiton.trainee.simple.devicemanagement.services.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityAlreadyExistsException;
import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityNotFoundException;
import com.progiton.trainee.simple.devicemanagement.mapper.SdmHandOverProtocolMapper;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmDeviceRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmHandOverProtocolRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmUserRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmHandOverProtocolService;

@Service
public class SdmHandOverProtocolServiceImpl implements SdmHandOverProtocolService {

	private final SdmHandOverProtocolRepository sdmHandOverProtocolRepository;
	private final SdmUserRepository sdmUserRepository;
	private final SdmDeviceRepository sdmDeviceRepository;
	private final SdmHandOverProtocolMapper mapper;

	public SdmHandOverProtocolServiceImpl(SdmHandOverProtocolRepository sdmHandOverProtocolRepository,
			SdmUserRepository sdmUserRepository, SdmDeviceRepository sdmDeviceRepository,
			SdmHandOverProtocolMapper mapper) {
		this.sdmHandOverProtocolRepository = sdmHandOverProtocolRepository;
		this.sdmUserRepository = sdmUserRepository;
		this.sdmDeviceRepository = sdmDeviceRepository;
		this.mapper = mapper;
	}

	@Override
	public List<SdmHandOverProtocolTo> findAllHandOverProtocols() {
		return mapper.toToList(sdmHandOverProtocolRepository.findAll());
	}

	@Override
	public SdmHandOverProtocolTo saveHandOverProtocol(SdmHandOverProtocolTo request) {

		// Check for existing non-confirmed protocols
		boolean hasNonConfirmed = sdmHandOverProtocolRepository
				.existsByDevice_SerialNumberAndIsConfirmedFalse(request.getDeviceSerialNumber());

		if (hasNonConfirmed) {
			throw new IllegalStateException(
					"Cannot create a new handover protocol: the device already has a non-confirmed protocol.");
		}

		// Lookup device and users
		SdmDeviceEntity device = sdmDeviceRepository.findBySerialNumber(request.getDeviceSerialNumber())
				.orElseThrow(() -> new SdmEntityAlreadyExistsException(
						"Device not found with name: " + request.getDeviceSerialNumber()));

		SdmUserEntity receiver = sdmUserRepository.findByUsernameIgnoreCase(request.getReceiverUsername()).orElseThrow(
				() -> new SdmEntityNotFoundException("Receiver user not found: " + request.getReceiverUsername()));
		SdmUserEntity performer = sdmUserRepository.findByUsernameIgnoreCase(request.getPerformedByUsername())
				.orElseThrow(() -> new SdmEntityNotFoundException(
						"PerformedBy user not found: " + request.getPerformedByUsername()));

		// Create and populate the entity
		SdmHandOverProtocolEntity entity = new SdmHandOverProtocolEntity();
		entity.setDevice(device);
		entity.setReceiver(receiver);
		entity.setPerformedBy(performer);
		entity.setHandoverDate(request.getHandoverDate() != null ? request.getHandoverDate() : Instant.now());
		entity.setComments(request.getComments());
		entity.setIsConfirmed(request.getIsConfirmed());
		entity.setConfirmedAt(request.getConfirmedAt());
		entity.setActionType(SdmActionType.valueOf(request.getActionType())); // Set the required action type

		// Save to database
		return mapper.toTo(sdmHandOverProtocolRepository.save(entity));
	}

	@Override
	public List<SdmHandOverProtocolTo> findHandOverProtocolsByReceiverUsername(String username) {
		// 1) Check if user exists
		if (!sdmUserRepository.existsByUsername(username)) {
			throw new SdmEntityNotFoundException("User not found with username: " + username);
		}

		// 2) Fetch protocols
		List<SdmHandOverProtocolEntity> protocols = sdmHandOverProtocolRepository.findByReceiver_Username(username);

		// 3) Check if user has any protocols
		if (protocols == null || protocols.isEmpty()) {
			throw new SdmEntityNotFoundException("No handover protocols found for user: " + username);
		}

		// 4) Convert and return
		return mapper.toToList(protocols);
	}

	@Override
	public List<SdmHandOverProtocolTo> findHandOverProtocolsByPerformerUsername(String username) {
		// 1) Check if user exists
		if (!sdmUserRepository.existsByUsername(username)) {
			throw new SdmEntityNotFoundException("User not found with username: " + username);
		}

		// 2) Fetch protocols
		List<SdmHandOverProtocolEntity> protocols = sdmHandOverProtocolRepository.findByPerformedBy_Username(username);

		// 3) Check if user has any protocols
		if (protocols == null || protocols.isEmpty()) {
			throw new SdmEntityNotFoundException("No handover protocols found for performer: " + username);
		}

		// 4) Convert and return
		return mapper.toToList(protocols);
	}

	@Override
	public List<SdmHandOverProtocolTo> findByDeviceSerialNumber(String serialNumber) {
		// 1) Validate that the device exists
		if (!sdmDeviceRepository.existsBySerialNumber(serialNumber)) {
			throw new SdmEntityNotFoundException("Device not found with serial number: " + serialNumber);
		}

		// 2) Fetch all protocols
		List<SdmHandOverProtocolEntity> protocols = sdmHandOverProtocolRepository
				.findAllByDevice_SerialNumber(serialNumber);

		// 3) Check if there are any protocols
		if (protocols == null || protocols.isEmpty()) {
			throw new SdmEntityNotFoundException("No handover protocols found for device: " + serialNumber);
		}

		// 4) Map to DTOs and return
		return mapper.toToList(protocols);
	}

	@Override
	public SdmHandOverProtocolTo findNonConfirmedProtocolsByDeviceSerialNumber(String serialNumber) {
		// 1) Validate device exists
		if (!sdmDeviceRepository.existsBySerialNumber(serialNumber)) {
			throw new SdmEntityNotFoundException("Device not found with serial number: " + serialNumber);
		}

		// 2) Fetch all non-confirmed protocols
		SdmHandOverProtocolEntity protocol = sdmHandOverProtocolRepository
				.findByDevice_SerialNumberAndIsConfirmedFalse(serialNumber);

		// 3) Fail if none
		if (protocol == null) {
			throw new SdmEntityNotFoundException(
					"No non-confirmed handover protocols found for device: " + serialNumber);
		}

		// 4) Map to DTOs
		return mapper.toTo(protocol);
	}

	@Override
	public SdmHandOverProtocolTo confirmByDeviceSerialNumber(String serialNumber) {
		// 1) Validate device exists
		if (!sdmDeviceRepository.existsBySerialNumber(serialNumber)) {
			throw new SdmEntityNotFoundException("Device not found with serial number: " + serialNumber);
		}

		// 2) Fetch the single non-confirmed protocol (if any)
		SdmHandOverProtocolEntity protocols = sdmHandOverProtocolRepository
				.findByDevice_SerialNumberAndIsConfirmedFalse(serialNumber);

		if (protocols == null) {
			throw new SdmEntityNotFoundException(
					"No non-confirmed handover protocols found for device: " + serialNumber);
		}

		// 3) Get the only protocol
		SdmHandOverProtocolEntity protocolToConfirm = protocols;

		// 4) Confirm it
		protocolToConfirm.setIsConfirmed(true);
		protocolToConfirm.setConfirmedAt(Instant.now());
		// 5) Update device ownership based on action type
		SdmActionType actionType = protocolToConfirm.getSdmActionType();
		SdmDeviceEntity device = protocolToConfirm.getDevice();

		if (actionType == SdmActionType.HANDOVER) {
			device.setUser(protocolToConfirm.getReceiver());
		} else if (actionType == SdmActionType.RETURN) {
			device.setUser(null);
		}

		// 6) Save both entities
		sdmHandOverProtocolRepository.save(protocolToConfirm);
		sdmDeviceRepository.save(device);

		// 7) Save and return
		SdmHandOverProtocolEntity saved = sdmHandOverProtocolRepository.save(protocolToConfirm);
		return mapper.toTo(saved);
	}

}
