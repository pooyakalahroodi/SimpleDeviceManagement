package com.progiton.trainee.simple.devicemanagement.mapper;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SdmDepartmentMapper {

    SdmDepartmentMapper INSTANCE = Mappers.getMapper(SdmDepartmentMapper.class);

    // Entity -> TO
    SdmDepartmentTo toTo(SdmDepartmentEntity entity);

    // TO -> Entity
    SdmDepartmentEntity toEntity(SdmDepartmentTo dto);

    // List mappings
    List<SdmDepartmentTo> toToList(List<SdmDepartmentEntity> entities);

    List<SdmDepartmentEntity> toEntityList(List<SdmDepartmentTo> dtos);
}
