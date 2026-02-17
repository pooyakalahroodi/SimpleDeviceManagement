package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.exceptions.SdmEntityAlreadyExistsException;
import com.progiton.trainee.simple.devicemanagement.exceptions.SdmValidationException;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceRegistrationOrderTo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Orchestrates device registration workflow with comprehensive validation.
 * Validates user existence, email/userId consistency, and device uniqueness.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SdmDeviceRegistrationService {

    private final SdmUserCoreService userService;
    private final SdmDeviceCoreService deviceService;

    /**
     * Validates if device already exists by serial number.
     *
     * @param serialNumber Device serial number to check
     * @throws SdmEntityAlreadyExistsException if device already exists
     */
    public void validateDeviceNotExists(String serialNumber) {
        log.debug("Validating device uniqueness: serial={}", serialNumber);

        if (deviceService.existsBySerialNumber(serialNumber)) {
            log.error("❌ Device already exists: serial={}", serialNumber);
            throw new SdmEntityAlreadyExistsException(
                    String.format("Device with serial number '%s' is already registered", serialNumber)
            );
        }

        log.debug("✓ Device serial is unique: {}", serialNumber);
    }

    /**
     * Validates user exists and userId matches email address.
     *
     * @param userId Expected user ID
     * @param email User email address
     * @return Validated user transfer object
     * @throws SdmValidationException if userId and email don't match
     */
    public SdmUserTo validateUser(UUID userId, String email) {
        log.debug("Validating user: userId={}, email={}", userId, email);

        // Find user by email
        SdmUserTo user = userService.findUserByEmailAddress(email);

        // Verify userId matches
        if (!user.getUserId().equals(userId)) {
            log.error("❌ UserId mismatch - Expected: {}, Received: {}", user.getUserId(), userId);
            throw new SdmValidationException(
                    String.format("User ID %s does not match email address %s (expected: %s)",
                            userId, email, user.getUserId())
            );
        }

        log.debug("✓ User validated: userId={}", user.getUserId());
        return user;
    }

    /**
     * Registers device after validation.
     * Uses validateUser() and validateDeviceNotExists() before storing device.
     *
     * @param order Device registration order
     */
    public void registerDevice(SdmDeviceRegistrationOrderTo order) {
        log.info("📥 Starting device registration: email={}, serial={}",
                order.getEmailAddress(), order.getSerialNumber());

        // Step 1: Validate user exists and matches
        SdmUserTo validatedUser = validateUser(order.getUserId(), order.getEmailAddress());

        // Step 2: Validate device doesn't exist
        validateDeviceNotExists(order.getSerialNumber());

        // Step 3: Create device DTO
        SdmDeviceTo device = new SdmDeviceTo();
        device.setName(order.getName());
        device.setType(order.getType());
        device.setStatus(order.getStatus());
        device.setSerialNumber(order.getSerialNumber());
        device.setManufacturer(order.getManufacturer());
        device.setLocation(order.getLocation());
        device.setPurchaseDate(order.getPurchaseDate());

        // Step 4: Store device in database
        deviceService.saveDevice(device);

        log.info("✅ Device registered successfully: serial={}, userId={}",
                device.getSerialNumber());
    }

}