package com.progiton.trainee.simple.devicemanagement.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Set;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SdmHandOverProtocolToTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static SdmHandOverProtocolTo validProtocol() {
        return new SdmHandOverProtocolTo(
                "SN-12345",
                "receiver1",
                "admin1",
                SdmActionType.HANDOVER,      // adjust to a real enum value in your project
                Instant.parse("2025-01-01T10:15:30Z"),
                "All good.",
                Boolean.TRUE,
                Instant.parse("2025-01-01T10:20:30Z")
        );
    }

    @Test
    void validProtocol_thenNoViolations() {
        SdmHandOverProtocolTo to = validProtocol();

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).isEmpty();
    }

    @Test
    void deviceSerialNumberBlank_thenViolationOnDeviceSerialNumber() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setDeviceSerialNumber("   "); // @NotBlank

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("deviceSerialNumber"));
    }

    @Test
    void receiverUsernameTooLong_thenViolationOnReceiverUsername() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setReceiverUsername("a".repeat(51)); // @Size(max=50)

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("receiverUsername"));
    }

    @Test
    void performedByUsernameNull_thenViolationOnPerformedByUsername() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setPerformedByUsername(null); // @NotBlank also fails for null in most providers

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("performedByUsername"));
    }

    @Test
    void actionTypeNull_thenViolationOnActionType() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setActionType(null); // @NotNull

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("actionType"));
    }

    @Test
    void handoverDateNull_thenViolationOnHandoverDate() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setHandoverDate(null); // @NotNull

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("handoverDate"));
    }

    @Test
    void commentsTooLong_thenViolationOnComments() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setComments("a".repeat(501)); // @Size(max=500)

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("comments"));
    }

    @Test
    void commentsNull_isAllowed_thenNoViolationsFromComments() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setComments(null);

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).isEmpty();
    }

    @Test
    void isConfirmedNull_thenViolationOnIsConfirmed() {
        SdmHandOverProtocolTo to = validProtocol();
        to.setIsConfirmed(null); // @NotNull

        Set<ConstraintViolation<SdmHandOverProtocolTo>> violations = validator.validate(to);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("isConfirmed"));
    }
}
