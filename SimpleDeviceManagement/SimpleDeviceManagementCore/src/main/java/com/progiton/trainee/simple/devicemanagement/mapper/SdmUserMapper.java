package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;

@Mapper(componentModel = "spring", uses = { SdmDepartmentMapper.class })
public interface SdmUserMapper {

    // Entity to DTO
    @Mapping(source = "devices", target = "devices")
    SdmUserTo toTo(SdmUserEntity user);

    // DTO to Entity for CREATE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "department", ignore = true) // handled in service
    @Mapping(target = "devices", ignore = true) // handled in service
    SdmUserEntity toEntity(SdmUserTo dto);

    // Update existing entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "devices", ignore = true)
    void updateEntityFromDto(SdmUserTo dto, @MappingTarget SdmUserEntity entity);

    // List conversions
    List<SdmUserTo> toToList(List<SdmUserEntity> users);

    
//    // Custom mapping for devices
//    @Named("deviceEntitiesToStrings")
//    default List<String> deviceEntitiesToStrings(List<SdmDeviceEntity> devices) {
//        if (devices == null) return null;
//        return devices.stream()
//                .map(device -> device.getName() + " (" + device.getSerialNumber() + ")")
//                .collect(Collectors.toList());
//    }
}