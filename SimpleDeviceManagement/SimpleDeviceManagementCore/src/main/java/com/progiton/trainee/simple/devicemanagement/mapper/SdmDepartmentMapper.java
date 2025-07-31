package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDepartmentTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDepartmentEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SdmDepartmentMapper {

	// Entity -> TO

	SdmDepartmentTo toTo(SdmDepartmentEntity entity);

	// TO -> Entity
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "userEntity", ignore = true)
	SdmDepartmentEntity toEntity(SdmDepartmentTo to);

	// List mappings
	@IterableMapping(elementTargetType = SdmDepartmentTo.class)
	List<SdmDepartmentTo> toToList(List<SdmDepartmentEntity> entities);

	@IterableMapping(elementTargetType = SdmDepartmentEntity.class)
	List<SdmDepartmentEntity> toEntityList(List<SdmDepartmentTo> dtos);
}
