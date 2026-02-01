package com.progiton.trainee.simple.devicemanagement.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SdmDeviceToTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static SdmDeviceTo validDevice() {
        return new SdmDeviceTo(
                "Laptop",
                "Notebook",
                SdmDeviceStatus.ACTIVE,
                "SN-12345",
                "Lenovo",
                "Office",
                LocalDate.of(2024, 1, 10)
        );
    }

    @Test
    void validDevice_thenNoViolations() {
        SdmDeviceTo device = validDevice();

        Set<ConstraintViolation<SdmDeviceTo>> violations = validator.validate(device);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankName_thenViolationOnName() {
        SdmDeviceTo device = validDevice();
        device.setName("   ");

        Set<ConstraintViolation<SdmDeviceTo>> violations = validator.validate(device);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void typeTooLong_thenViolationOnType() {
        SdmDeviceTo device = validDevice();
        device.setType("a".repeat(51)); // max = 50

        Set<ConstraintViolation<SdmDeviceTo>> violations = validator.validate(device);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("type"));
    }

    @Test
    void statusNull_thenViolationOnStatus() {
        SdmDeviceTo device = validDevice();
        device.setStatus(null);

        Set<ConstraintViolation<SdmDeviceTo>> violations = validator.validate(device);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("status"));
    }

    @Test
    void purchaseDateNull_thenViolationOnPurchaseDate() {
        SdmDeviceTo device = validDevice();
        device.setPurchaseDate(null);

        Set<ConstraintViolation<SdmDeviceTo>> violations = validator.validate(device);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("purchaseDate"));
    }

    @Test
    void settersAndGetters_work() {
        SdmDeviceTo device = new SdmDeviceTo();
        device.setName("Phone");
        device.setType("Mobile");
        device.setStatus(SdmDeviceStatus.ACTIVE);
        device.setSerialNumber("SN-999");
        device.setManufacturer("Samsung");
        device.setLocation("HQ");
        device.setPurchaseDate(LocalDate.of(2023, 6, 1));

        assertThat(device.getName()).isEqualTo("Phone");
        assertThat(device.getType()).isEqualTo("Mobile");
        assertThat(device.getStatus()).isEqualTo(SdmDeviceStatus.ACTIVE);
        assertThat(device.getSerialNumber()).isEqualTo("SN-999");
        assertThat(device.getManufacturer()).isEqualTo("Samsung");
        assertThat(device.getLocation()).isEqualTo("HQ");
        assertThat(device.getPurchaseDate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }
}