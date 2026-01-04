package com.progiton.trainee.simple.devicemanagement.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SdmDepartmentToTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void defaultConstructor_thenSetterGetterWork() {
        SdmDepartmentTo to = new SdmDepartmentTo();
        to.setName("IT");

        assertThat(to.getName()).isEqualTo("IT");
    }

    @Test
    void allArgsConstructor_setsName() {
        SdmDepartmentTo to = new SdmDepartmentTo("HR");

        assertThat(to.getName()).isEqualTo("HR");
    }

    @Test
    void validName_thenNoViolations() {
        SdmDepartmentTo to = new SdmDepartmentTo("Finance");

        Set<ConstraintViolation<SdmDepartmentTo>> violations = validator.validate(to);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullName_thenNotBlankViolationOnName() {
        SdmDepartmentTo to = new SdmDepartmentTo(null);

        Set<ConstraintViolation<SdmDepartmentTo>> violations = validator.validate(to);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")); // property path contains field name [web:33]
    }

    @Test
    void blankName_thenNotBlankViolationOnName() {
        SdmDepartmentTo to = new SdmDepartmentTo("   ");

        Set<ConstraintViolation<SdmDepartmentTo>> violations = validator.validate(to);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void nameLongerThan50_thenSizeViolationOnName() {
        String longName = "a".repeat(51);
        SdmDepartmentTo to = new SdmDepartmentTo(longName);

        Set<ConstraintViolation<SdmDepartmentTo>> violations = validator.validate(to);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void nameExactly50_thenNoViolations() {
        String maxName = "a".repeat(50);
        SdmDepartmentTo to = new SdmDepartmentTo(maxName);

        Set<ConstraintViolation<SdmDepartmentTo>> violations = validator.validate(to);

        assertThat(violations).isEmpty();
    }
}