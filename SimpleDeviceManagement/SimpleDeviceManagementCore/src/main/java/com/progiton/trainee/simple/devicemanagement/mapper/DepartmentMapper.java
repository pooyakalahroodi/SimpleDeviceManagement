package com.progiton.trainee.simple.devicemanagement.mapper;

import com.progiton.trainee.simple.devicemanagement.model.to.DepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    // Entity -> TO
    DepartmentTo toTo(DepartmentEntity entity);

    // TO -> Entity
    DepartmentEntity toEntity(DepartmentTo dto);

    // List mappings
    List<DepartmentTo> toToList(List<DepartmentEntity> entities);

    List<DepartmentEntity> toEntityList(List<DepartmentTo> dtos);
}
