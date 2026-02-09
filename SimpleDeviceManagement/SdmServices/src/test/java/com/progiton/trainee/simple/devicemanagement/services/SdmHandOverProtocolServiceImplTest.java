package com.progiton.trainee.simple.devicemanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import com.progiton.trainee.simple.devicemanagement.services.impl.SdmHandOverProtocolServiceImpl;

@ExtendWith(MockitoExtension.class)
class SdmHandOverProtocolServiceImplTest {

	@Mock
	private SdmHandOverProtocolCoreService sdmHandOverProtocolCoreService;

	@InjectMocks
	private SdmHandOverProtocolServiceImpl sdmHandOverProtocolService;

	@Test
	void findAllHandOverProtocols_DelegatesToCoreService() {
		List<SdmHandOverProtocolTo> expected = Collections.singletonList(new SdmHandOverProtocolTo());
		when(sdmHandOverProtocolCoreService.findAllHandOverProtocols()).thenReturn(expected);

		List<SdmHandOverProtocolTo> result = sdmHandOverProtocolService.findAllHandOverProtocols();

		assertThat(result).isEqualTo(expected);
		verify(sdmHandOverProtocolCoreService).findAllHandOverProtocols();
	}

	@Test
	void saveHandOverProtocol_DelegatesToCoreService() {
		SdmHandOverProtocolTo request = new SdmHandOverProtocolTo();
		when(sdmHandOverProtocolCoreService.saveHandOverProtocol(request)).thenReturn(request);

		SdmHandOverProtocolTo result = sdmHandOverProtocolService.saveHandOverProtocol(request);

		assertThat(result).isEqualTo(request);
		verify(sdmHandOverProtocolCoreService).saveHandOverProtocol(request);
	}

	@Test
	void findHandOverProtocolsByReceiverUserId_DelegatesToCoreService() {

		UUID receiverUserId = UUID.randomUUID();
		List<SdmHandOverProtocolTo> expected = Collections.singletonList(new SdmHandOverProtocolTo());
		when(sdmHandOverProtocolCoreService.findHandOverProtocolsByReceiverUserId(receiverUserId)).thenReturn(expected);

		List<SdmHandOverProtocolTo> result = sdmHandOverProtocolService.findHandOverProtocolsByReceiverUserId(receiverUserId);

		assertThat(result).isEqualTo(expected);
		verify(sdmHandOverProtocolCoreService).findHandOverProtocolsByReceiverUserId(receiverUserId);
	}

	@Test
	void findHandOverProtocolsByPerformerUsername_DelegatesToCoreService() {
		UUID receiverUserId = UUID.randomUUID();
		List<SdmHandOverProtocolTo> expected = Collections.singletonList(new SdmHandOverProtocolTo());
		when(sdmHandOverProtocolCoreService.findHandOverProtocolsByPerformerUserId(receiverUserId)).thenReturn(expected);

		List<SdmHandOverProtocolTo> result = sdmHandOverProtocolService.findHandOverProtocolsByPerformerUsername(receiverUserId);

		assertThat(result).isEqualTo(expected);
		verify(sdmHandOverProtocolCoreService).findHandOverProtocolsByPerformerUserId(receiverUserId);
	}

	@Test
	void findByDeviceSerialNumber_DelegatesToCoreService() {
		List<SdmHandOverProtocolTo> expected = Collections.singletonList(new SdmHandOverProtocolTo());
		when(sdmHandOverProtocolCoreService.findByDeviceSerialNumber("SN123")).thenReturn(expected);

		List<SdmHandOverProtocolTo> result = sdmHandOverProtocolService.findByDeviceSerialNumber("SN123");

		assertThat(result).isEqualTo(expected);
		verify(sdmHandOverProtocolCoreService).findByDeviceSerialNumber("SN123");
	}

	@Test
	void findNonConfirmedProtocolsByDeviceSerialNumber_DelegatesToCoreService() {
		SdmHandOverProtocolTo expected = new SdmHandOverProtocolTo();
		when(sdmHandOverProtocolCoreService.findNonConfirmedProtocolsByDeviceSerialNumber("SN123")).thenReturn(expected);

		SdmHandOverProtocolTo result = sdmHandOverProtocolService.findNonConfirmedProtocolsByDeviceSerialNumber("SN123");

		assertThat(result).isEqualTo(expected);
		verify(sdmHandOverProtocolCoreService).findNonConfirmedProtocolsByDeviceSerialNumber("SN123");
	}

	@Test
	void confirmByDeviceSerialNumber_DelegatesToCoreService() {
		SdmHandOverProtocolTo expected = new SdmHandOverProtocolTo();
		when(sdmHandOverProtocolCoreService.confirmByDeviceSerialNumber("SN123")).thenReturn(expected);

		SdmHandOverProtocolTo result = sdmHandOverProtocolService.confirmByDeviceSerialNumber("SN123");

		assertThat(result).isEqualTo(expected);
		verify(sdmHandOverProtocolCoreService).confirmByDeviceSerialNumber("SN123");
	}
}
