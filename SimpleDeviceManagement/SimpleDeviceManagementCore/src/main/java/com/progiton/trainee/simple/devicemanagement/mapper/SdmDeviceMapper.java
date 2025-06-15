package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;

@Mapper(componentModel = "spring")
public interface SdmDeviceMapper {


    @Mapping(source = "assignedToUsername", target = "assignedToUsername")
    @Mapping(source = "status", target = "status")
    SdmDeviceTo toTo(SdmDeviceEntity device);

    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "status", expression = "java(mapStatus(dto.getStatus()))")
    SdmDeviceEntity toEntity(SdmDeviceTo dto);

    List<SdmDeviceTo> toToList(List<SdmDeviceEntity> devices);
    List<SdmDeviceEntity> toEntityList(List<SdmDeviceTo> dtos);

    default SdmDeviceStatus mapStatus(String status) {
        return status != null ? SdmDeviceStatus.valueOf(status) : null;
    }
}
