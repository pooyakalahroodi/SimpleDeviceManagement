package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SdmDepartmentMapper {

	SdmDepartmentMapper INSTANCE = Mappers.getMapper(SdmDepartmentMapper.class);

	// Entity -> TO

	SdmDepartmentTo toTo(SdmDepartmentEntity entity);

	// TO -> Entity
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "userEntity", ignore = true)
	SdmDepartmentEntity toEntity(SdmDepartmentTo dto);

	// List mappings
	List<SdmDepartmentTo> toToList(List<SdmDepartmentEntity> entities);

	List<SdmDepartmentEntity> toEntityList(List<SdmDepartmentTo> dtos);
}
