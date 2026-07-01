package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmHandOverProtocolCoreServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SdmHandOverProtocolCoreServiceImplTest {

	@Mock
	private SdmHandOverProtocolRepository protocolRepository;

	@Mock
	private SdmUserRepository userRepository;

	@Mock
	private SdmDeviceRepository deviceRepository;

	@Mock
	private SdmHandOverProtocolMapper mapper;

	@InjectMocks
	private SdmHandOverProtocolCoreServiceImpl service;

	private SdmHandOverProtocolTo requestTo;
	private SdmHandOverProtocolEntity savedEntity;
	private SdmDeviceEntity deviceEntity;
	private SdmUserEntity receiverUser;
	private SdmUserEntity performerUser;
	private SdmHandOverProtocolTo savedTo;
	private UUID receiverUuid;
	private UUID performerUuid;

	@BeforeEach
	void setUp() {
		receiverUuid = UUID.randomUUID();
		performerUuid = UUID.randomUUID();

		requestTo = new SdmHandOverProtocolTo();
		requestTo.setDeviceSerialNumber("SN123");
		requestTo.setReceiverUserId(receiverUuid);       // ✅ UUID instead of String
		requestTo.setReceiverUserId(receiverUuid);       // ✅ UUID instead of String
		requestTo.setPerformedByUserId(performerUuid);   // ✅ UUID instead of String
		requestTo.setActionType(SdmActionType.HANDOVER);
		requestTo.setIsConfirmed(true);
		requestTo.setHandoverDate(Instant.now());

		deviceEntity = new SdmDeviceEntity();
		deviceEntity.setSerialNumber("SN123");

		receiverUser = new SdmUserEntity();
		receiverUser.setUserId(receiverUuid);  // Match the UUID
		receiverUser.setEmailAddress("receiver@example.com");

		performerUser = new SdmUserEntity();
		performerUser.setUserId(performerUuid);  // Match the UUID
		performerUser.setEmailAddress("performer@example.com");

		savedEntity = new SdmHandOverProtocolEntity();
		savedEntity.setReceiver(receiverUser);
		savedEntity.setPerformedBy(performerUser);
		savedEntity.setDevice(deviceEntity);

		savedTo = new SdmHandOverProtocolTo();
	}

	@Test
	void findAllHandOverProtocols_ReturnsList() {
		List<SdmHandOverProtocolEntity> entities = Arrays.asList(savedEntity);
		List<SdmHandOverProtocolTo> tos = Arrays.asList(savedTo);

		when(protocolRepository.findAll()).thenReturn(entities);
		when(mapper.toToList(entities)).thenReturn(tos);

		List<SdmHandOverProtocolTo> result = service.findAllHandOverProtocols();

		assertThat(result).isEqualTo(tos);

		verify(protocolRepository).findAll();
		verify(mapper).toToList(entities);
	}

	@Test
	void saveHandOverProtocol_Success() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(false);
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUserId(receiverUuid)).thenReturn(Optional.of(receiverUser));
		when(userRepository.findByUserId(performerUuid)).thenReturn(Optional.of(performerUser));
		when(protocolRepository.save(any(SdmHandOverProtocolEntity.class))).thenReturn(savedEntity);
		when(mapper.toTo(savedEntity)).thenReturn(savedTo);

		SdmHandOverProtocolTo result = service.saveHandOverProtocol(requestTo);

		assertThat(result).isEqualTo(savedTo);

		verify(protocolRepository).existsByDevice_SerialNumberAndIsConfirmedFalse("SN123");
		verify(deviceRepository).findBySerialNumber("SN123");
		verify(userRepository).findByUserId(receiverUuid);
		verify(userRepository).findByUserId(performerUuid);
		verify(protocolRepository).save(any(SdmHandOverProtocolEntity.class));
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenNonConfirmedProtocolExists() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(true);

		IllegalStateException ex = assertThrows(IllegalStateException.class,
				() -> service.saveHandOverProtocol(requestTo));
		assertThat(ex.getMessage()).contains("non-confirmed protocol");
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenDeviceNotFound() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(false);
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> service.saveHandOverProtocol(requestTo));
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenReceiverNotFound() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(false);
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUserId(receiverUuid)).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> service.saveHandOverProtocol(requestTo));
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenPerformerNotFound() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(false);
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUserId(receiverUuid)).thenReturn(Optional.of(receiverUser));
		when(userRepository.findByUserId(performerUuid)).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> service.saveHandOverProtocol(requestTo));
	}

	@Test
	void findHandOverProtocolsByReceiverUserId_Success() {
		when(userRepository.existsByUserId(receiverUuid)).thenReturn(true);
		List<SdmHandOverProtocolEntity> entities = Arrays.asList(savedEntity);
		List<SdmHandOverProtocolTo> tos = Arrays.asList(savedTo);

		when(protocolRepository.findByReceiver_UserId(receiverUuid)).thenReturn(entities);
		when(mapper.toToList(entities)).thenReturn(tos);

		List<SdmHandOverProtocolTo> result = service.findHandOverProtocolsByReceiverUserId(receiverUuid);

		assertThat(result).isEqualTo(tos);

		verify(userRepository).existsByUserId(receiverUuid);
		verify(protocolRepository).findByReceiver_UserId(receiverUuid);
		verify(mapper).toToList(entities);
	}

	@Test
	void findHandOverProtocolsByReceiverUserId_UserNotFound() {
		when(userRepository.existsByUserId(receiverUuid)).thenReturn(false);

		assertThrows(SdmEntityNotFoundException.class,
				() -> service.findHandOverProtocolsByReceiverUserId(receiverUuid));

		verify(userRepository).existsByUserId(receiverUuid);
		verifyNoInteractions(protocolRepository);
	}
}
