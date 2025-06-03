package com.progiton.trainee.simple.devicemanagement.to;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;
import com.progiton.trainee.simple.devicemanagement.view.DeviceView;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceTo implements DeviceView{

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