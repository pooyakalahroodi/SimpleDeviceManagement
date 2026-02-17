package com.progiton.trainee.simple.devicemanagement.inbound.jms;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceRegistrationOrderTo;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceRegistrationService;
import com.progiton.trainee.simple.devicemanagement.services.SdmDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SdmDeviceRegistrationListener {
    private final SdmDeviceRegistrationService sdmDeviceRegistrationService;

    /**
     * Listens for device registration orders on ActiveMQ queue.
     * Automatically deserializes JSON → DeviceUserRegistrationOrderTo.
     */
    @JmsListener(
            destination = "device-user-registration-queue",
            containerFactory = "jmsListenerContainerFactory"
    )
    public void handleDeviceRegistration(SdmDeviceRegistrationOrderTo order) {
        sdmDeviceRegistrationService.registerDevice(order);  // ← Single method call

        log.info("✅ Processed registration: serial={} user={}",
                order.getSerialNumber(), order.getEmailAddress());
    }
}
