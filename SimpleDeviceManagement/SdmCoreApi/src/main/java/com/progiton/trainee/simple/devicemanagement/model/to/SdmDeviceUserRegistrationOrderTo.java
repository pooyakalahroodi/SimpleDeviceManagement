package com.progiton.trainee.simple.devicemanagement.model.to;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;


@Data @NoArgsConstructor @AllArgsConstructor
public class SdmDeviceUserRegistrationOrderTo {
    @Valid private SdmUserTo user;    // Check/create user
    @Valid private SdmDeviceTo device; // Assign to user, add device
}
