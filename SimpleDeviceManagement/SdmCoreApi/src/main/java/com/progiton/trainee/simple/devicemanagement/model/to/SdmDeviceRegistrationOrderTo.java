package com.progiton.trainee.simple.devicemanagement.model.to;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Data @NoArgsConstructor @AllArgsConstructor
public class SdmDeviceRegistrationOrderTo implements Serializable {
    // user data attached to the message
    private UUID userId;

    @NotBlank
    @Email
    @Size(min=4, max = 50)
    private String emailAddress;

    // device data attached to the message
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String type;

    @NotNull
    private SdmDeviceStatus status;

    @NotBlank
    @Size(max = 50)
    private String serialNumber;

    @NotBlank
    @Size(max = 100)
    private String manufacturer;

    @NotBlank
    @Size(max = 100)
    private String location;

    @NotNull
    private LocalDate purchaseDate;
}
