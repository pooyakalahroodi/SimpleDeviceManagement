package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmUserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmUserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmRoleEntity;

@Mapper(componentModel = "spring")
public interface SdmUserMapper {

    // Entity to DTO - convert entity relationships to simple strings
    @Mapping(source = "departmentEntity.name", target = "department")
    @Mapping(source = "deviceEntities", target = "devices", qualifiedByName = "deviceEntitiesToStrings")
    SdmUserTo toTo(SdmUserEntity user);

    // DTO to Entity for CREATE operations - ignore complex relationships
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true) // Handle in service layer
    @Mapping(target = "deviceEntities", ignore = true)   // Handle in service layer
    @Mapping(target = "roleEntities", ignore = true)     // Handle in service layer
    SdmUserEntity toEntity(SdmUserTo dto);

    // DTO to Entity for UPDATE operations
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true) // Handle in service layer
    @Mapping(target = "deviceEntities", ignore = true)   // Handle in service layer
    @Mapping(target = "roleEntities", ignore = true)     // Handle in service layer
    SdmUserEntity toEntityWithId(SdmUserTo dto);

    // Update existing entity with DTO data
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true)
    @Mapping(target = "deviceEntities", ignore = true)
    @Mapping(target = "roleEntities", ignore = true)
    void updateEntityFromDto(SdmUserTo dto, @MappingTarget SdmUserEntity entity);

    // List conversions
    List<SdmUserTo> toToList(List<SdmUserEntity> users);

    // Custom mapping methods for converting entities to strings
    @Named("deviceEntitiesToStrings")
    default List<String> deviceEntitiesToStrings(List<SdmDeviceEntity> devices) {
        if (devices == null) return null;
        return devices.stream()
                .map(device -> device.getName() + " (" + device.getSerialNumber() + ")") 
                .collect(Collectors.toList());
    }

    @Named("roleEntitiesToStrings")
    default List<String> roleEntitiesToStrings(List<SdmRoleEntity> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> String.valueOf(role.getId())) // Use ID as fallback
                .collect(Collectors.toList());
    }
}