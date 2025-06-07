package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.progiton.trainee.simple.devicemanagement.model.to.UserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.RoleEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity to DTO - convert entity relationships to simple strings
    @Mapping(source = "departmentEntity.name", target = "department")
    @Mapping(source = "deviceEntities", target = "devices", qualifiedByName = "deviceEntitiesToStrings")
    UserTo toTo(UserEntity user);

    // DTO to Entity for CREATE operations - ignore complex relationships
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true) // Handle in service layer
    @Mapping(target = "deviceEntities", ignore = true)   // Handle in service layer
    @Mapping(target = "roleEntities", ignore = true)     // Handle in service layer
    UserEntity toEntity(UserTo dto);

    // DTO to Entity for UPDATE operations
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true) // Handle in service layer
    @Mapping(target = "deviceEntities", ignore = true)   // Handle in service layer
    @Mapping(target = "roleEntities", ignore = true)     // Handle in service layer
    UserEntity toEntityWithId(UserTo dto);

    // Update existing entity with DTO data
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true)
    @Mapping(target = "deviceEntities", ignore = true)
    @Mapping(target = "roleEntities", ignore = true)
    void updateEntityFromDto(UserTo dto, @MappingTarget UserEntity entity);

    // List conversions
    List<UserTo> toToList(List<UserEntity> users);

    // Custom mapping methods for converting entities to strings
    @Named("deviceEntitiesToStrings")
    default List<String> deviceEntitiesToStrings(List<DeviceEntity> devices) {
        if (devices == null) return null;
        return devices.stream()
                .map(device -> device.getName() + " (" + device.getSerialNumber() + ")") 
                .collect(Collectors.toList());
    }

    @Named("roleEntitiesToStrings")
    default List<String> roleEntitiesToStrings(List<RoleEntity> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> String.valueOf(role.getId())) // Use ID as fallback
                .collect(Collectors.toList());
    }
}