package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.model.to.DeviceTo;

@Mapper(componentModel = "spring")
public interface DeviceMapper {


    @Mapping(source = "assignedToUsername", target = "assignedToUsername")
    @Mapping(source = "status", target = "status")
    DeviceTo toTo(DeviceEntity device);

    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "status", expression = "java(mapStatus(dto.getStatus()))")
    DeviceEntity toEntity(DeviceTo dto);

    List<DeviceTo> toToList(List<DeviceEntity> devices);
    List<DeviceEntity> toEntityList(List<DeviceTo> dtos);

    default DeviceStatus mapStatus(String status) {
        return status != null ? DeviceStatus.valueOf(status) : null;
    }
}
