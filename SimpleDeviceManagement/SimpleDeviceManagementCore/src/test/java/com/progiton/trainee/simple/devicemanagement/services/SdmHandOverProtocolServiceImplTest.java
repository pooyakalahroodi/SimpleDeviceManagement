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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmHandOverProtocolServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SdmHandOverProtocolServiceImplTest {

	@Mock
	private SdmHandOverProtocolRepository protocolRepository;

	@Mock
	private SdmUserRepository userRepository;

	@Mock
	private SdmDeviceRepository deviceRepository;

	@Mock
	private SdmHandOverProtocolMapper mapper;

	@InjectMocks
	private SdmHandOverProtocolServiceImpl service;

	private SdmHandOverProtocolTo requestTo;
	private SdmHandOverProtocolEntity savedEntity;
	private SdmDeviceEntity deviceEntity;
	private SdmUserEntity receiverUser;
	private SdmUserEntity performerUser;
	private SdmHandOverProtocolTo savedTo;

	@BeforeEach
	void setUp() {
		requestTo = new SdmHandOverProtocolTo();
		requestTo.setDeviceSerialNumber("SN123");
		requestTo.setReceiverUsername("receiver");
		requestTo.setPerformedByUsername("performer");
		requestTo.setActionType(SdmActionType.HANDOVER);
		requestTo.setIsConfirmed(true);
		requestTo.setHandoverDate(Instant.now());

		deviceEntity = new SdmDeviceEntity();
		receiverUser = new SdmUserEntity();
		performerUser = new SdmUserEntity();
		savedEntity = new SdmHandOverProtocolEntity();
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
		when(userRepository.findByUsernameIgnoreCase("receiver")).thenReturn(Optional.of(receiverUser));
		when(userRepository.findByUsernameIgnoreCase("performer")).thenReturn(Optional.of(performerUser));
		when(protocolRepository.save(any(SdmHandOverProtocolEntity.class))).thenReturn(savedEntity);
		when(mapper.toTo(savedEntity)).thenReturn(savedTo);

		SdmHandOverProtocolTo result = service.saveHandOverProtocol(requestTo);

		assertThat(result).isEqualTo(savedTo);

		verify(protocolRepository).existsByDevice_SerialNumberAndIsConfirmedFalse("SN123");
		verify(deviceRepository).findBySerialNumber("SN123");
		verify(userRepository).findByUsernameIgnoreCase("receiver");
		verify(userRepository).findByUsernameIgnoreCase("performer");
		verify(protocolRepository).save(any(SdmHandOverProtocolEntity.class));
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenDeviceSerialNumberMissing() {
		requestTo.setDeviceSerialNumber(null);

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.saveHandOverProtocol(requestTo));

		assertThat(ex.getMessage()).contains("Device serial number");
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenReceiverMissing() {
		requestTo.setReceiverUsername("");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.saveHandOverProtocol(requestTo));
		assertThat(ex.getMessage()).contains("Receiver username");
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenPerformedByMissing() {
		requestTo.setPerformedByUsername("   ");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.saveHandOverProtocol(requestTo));
		assertThat(ex.getMessage()).contains("Performed by username");
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenActionTypeMissing() {

//		when(deviceRepository.findBySerialNumber(requestTo.getDeviceSerialNumber())).thenReturn(Optional.of(deviceEntity));
//		when(userRepository.findByUsernameIgnoreCase(requestTo.getReceiverUsername())).thenReturn(Optional.of(receiverUser));
//		when(userRepository.findByUsernameIgnoreCase(requestTo.getPerformedByUsername())).thenReturn(Optional.of(performerUser));
		requestTo.setActionType(null);

		NullPointerException ex = assertThrows(NullPointerException.class,
				() -> service.saveHandOverProtocol(requestTo));
		assertThat(ex.getMessage()).contains("ActionType");
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

		assertThrows(SdmEntityAlreadyExistsException.class, () -> service.saveHandOverProtocol(requestTo));
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenReceiverNotFound() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(false);
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUsernameIgnoreCase("receiver")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> service.saveHandOverProtocol(requestTo));
	}

	@Test
	void saveHandOverProtocol_ThrowsWhenPerformerNotFound() {
		when(protocolRepository.existsByDevice_SerialNumberAndIsConfirmedFalse("SN123")).thenReturn(false);
		when(deviceRepository.findBySerialNumber("SN123")).thenReturn(Optional.of(deviceEntity));
		when(userRepository.findByUsernameIgnoreCase("receiver")).thenReturn(Optional.of(receiverUser));
		when(userRepository.findByUsernameIgnoreCase("performer")).thenReturn(Optional.empty());

		assertThrows(SdmEntityNotFoundException.class, () -> service.saveHandOverProtocol(requestTo));
	}

	@Test
	void findHandOverProtocolsByReceiverUsername_Success() {
		when(userRepository.existsByUsername("receiver")).thenReturn(true);
		List<SdmHandOverProtocolEntity> entities = Arrays.asList(savedEntity);
		List<SdmHandOverProtocolTo> tos = Arrays.asList(savedTo);

		when(protocolRepository.findByReceiver_Username("receiver")).thenReturn(entities);
		when(mapper.toToList(entities)).thenReturn(tos);

		List<SdmHandOverProtocolTo> result = service.findHandOverProtocolsByReceiverUsername("receiver");

		assertThat(result).isEqualTo(tos);

		verify(userRepository).existsByUsername("receiver");
		verify(protocolRepository).findByReceiver_Username("receiver");
		verify(mapper).toToList(entities);
	}

	@Test
	void findHandOverProtocolsByReceiverUsername_UserNotFound() {
		when(userRepository.existsByUsername("receiver")).thenReturn(false);

		assertThrows(SdmEntityNotFoundException.class,
				() -> service.findHandOverProtocolsByReceiverUsername("receiver"));

		verify(userRepository).existsByUsername("receiver");
		verifyNoInteractions(protocolRepository);
	}
}
