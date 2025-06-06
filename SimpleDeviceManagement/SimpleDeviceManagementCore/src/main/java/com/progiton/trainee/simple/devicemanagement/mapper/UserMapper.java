package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.progiton.trainee.simple.devicemanagement.model.to.UserTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity to DTO - these fields are computed by getter methods in UserEntity
    UserTo toTo(UserEntity user);

    // DTO to Entity - ignore entity-specific fields and relationships
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "departmentEntity", ignore = true)
    @Mapping(target = "deviceEntities", ignore = true)
    @Mapping(target = "roleEntities", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toEntity(UserTo dto);

    List<UserTo> toToList(List<UserEntity> users);
    List<UserEntity> toEntityList(List<UserTo> dtos);
    
}