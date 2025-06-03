package com.progiton.trainee.simple.devicemanagement.mapper;

import com.progiton.trainee.simple.devicemanagement.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.to.UserTo;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserTo toDto(UserEntity userEntity) {
        return UserTo.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .departmentId(userEntity.getDepartmentEntity() != null ? userEntity.getDepartmentEntity().getId() : null)
                .deviceIds(userEntity.getDeviceEntities() != null ? 
                    userEntity.getDeviceEntities().stream()
                        .map(device -> device.getId())
                        .collect(Collectors.toList()) : null)
                .roleIds(userEntity.getRoleEntities() != null ? 
                    userEntity.getRoleEntities().stream()
                        .map(role -> role.getId())
                        .collect(Collectors.toSet()) : null)
                .build();
    }

    public static UserEntity toEntity(UserTo dto) {
        System.out.println("🔁 Mapping UserDTO to Entity...");
        System.out.println("DTO Username: " + dto.getUsername());
        return UserEntity.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .name(dto.getName())
                .build(); // Doesn't set department, devices, or roles without respective services
    }

    public static List<UserTo> toDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public static List<UserEntity> toEntityList(List<UserTo> dtos) {
        return dtos.stream().map(UserMapper::toEntity).collect(Collectors.toList());
    }
}