package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;
import com.progiton.trainee.simple.devicemanagement.view.Device;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceTo implements Device {

    private String name; // No need to define getters, Lombok understands and takes care of it
    private String type;
    private DeviceStatus status;
    private String assignedToName;
    private String serialNumber;
    private String manufacturer;
    private String location;
    private LocalDate purchaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}