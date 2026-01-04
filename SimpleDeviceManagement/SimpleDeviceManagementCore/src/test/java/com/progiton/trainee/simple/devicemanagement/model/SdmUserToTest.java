package com.progiton.trainee.simple.devicemanagement.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SdmUserToTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ---------- Helpers ----------
    private static SdmDepartmentTo validDepartment() {
        return new SdmDepartmentTo("IT");
    }

    private static SdmUserTo validUser() {
        return new SdmUserTo(
                "john",              // min 4
                "John",              // not blank, max 100
                "Doe",               // not blank, max 100
                Boolean.TRUE,        // not null
                validDepartment(),   // not null + cascaded @Valid
                List.of()            // optional, but if present: cascaded @Valid
        );
    }

    // ---------- POJO tests ----------
    @Test
    void allArgsConstructor_setsFields() {
        SdmDepartmentTo dep = new SdmDepartmentTo("HR");
        SdmUserTo user = new SdmUserTo("user1", "Max", "Mustermann", true, dep, List.of());

        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getName()).isEqualTo("Max");
        assertThat(user.getSurname()).isEqualTo("Mustermann");
        assertThat(user.getEnabled()).isTrue();
        assertThat(user.getDepartment()).isSameAs(dep);
        assertThat(user.getDevices()).isEmpty();
    }

    @Test
    void settersAndGetters_work() {
        SdmUserTo user = new SdmUserTo();
        user.setUsername("user1");
        user.setName("Max");
        user.setSurname("Mustermann");
        user.setEnabled(false);
        user.setDepartment(new SdmDepartmentTo("IT"));
        user.setDevices(List.of());

        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getName()).isEqualTo("Max");
        assertThat(user.getSurname()).isEqualTo("Mustermann");
        assertThat(user.getEnabled()).isFalse();
        assertThat(user.getDepartment().getName()).isEqualTo("IT");
        assertThat(user.getDevices()).isEmpty();
    }

    // ---------- Validation tests ----------
    @Test
    void validUser_thenNoViolations() {
        SdmUserTo user = validUser();

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    @Test
    void usernameTooShort_thenViolationOnUsername() {
        SdmUserTo user = validUser();
        user.setUsername("abc"); // min = 4

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("username"));
    }

    @Test
    void usernameBlank_thenViolationOnUsername() {
        SdmUserTo user = validUser();
        user.setUsername("   "); // @NotBlank

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("username"));
    }

    @Test
    void enabledNull_thenViolationOnEnabled() {
        SdmUserTo user = validUser();
        user.setEnabled(null); // @NotNull

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("enabled"));
    }

    @Test
    void departmentNull_thenViolationOnDepartment() {
        SdmUserTo user = validUser();
        user.setDepartment(null); // @NotNull

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("department"));
    }

    @Test
    void invalidDepartmentName_thenCascadedViolationOnDepartmentName() {
        SdmUserTo user = validUser();
        user.setDepartment(new SdmDepartmentTo("")); // invalid because SdmDepartmentTo.name is @NotBlank

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        // cascaded property path should include nested path like "department.name"
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("department.name"));
    }

    /**
     * Only include this test if SdmDeviceTo actually has constraints (e.g., @NotBlank description).
     * If SdmDeviceTo has no constraints, validating devices will produce no violations.
     */
    @Test
    void invalidDeviceInList_thenCascadedViolationOnDevicesElement() {
        SdmUserTo user = validUser();

        // Example assumes SdmDeviceTo has a @NotBlank field like "description"
        SdmDeviceTo invalidDevice = new SdmDeviceTo(); // or new SdmDeviceTo("")
        user.setDevices(List.of(invalidDevice));

        Set<ConstraintViolation<SdmUserTo>> violations = validator.validate(user);

        // For lists, the property path often contains an index and a "<list element>" node, depending on provider/version.
        // So check "startsWith" rather than exact equals.
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().startsWith("devices"));
    }
}