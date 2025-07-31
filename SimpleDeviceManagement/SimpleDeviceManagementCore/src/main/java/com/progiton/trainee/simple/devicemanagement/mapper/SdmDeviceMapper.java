package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;

@Mapper(componentModel = "spring")
public interface SdmDeviceMapper {

	// ENTITY -> DTO
	SdmDeviceTo toTo(SdmDeviceEntity device);

	// DTO -> ENTITY
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	SdmDeviceEntity toEntity(SdmDeviceTo to);

	@IterableMapping(elementTargetType = SdmDeviceTo.class)
	List<SdmDeviceTo> toToList(List<SdmDeviceEntity> devices);

	@IterableMapping(elementTargetType = SdmDeviceEntity.class)
	List<SdmDeviceEntity> toEntityList(List<SdmDeviceTo> dtos);

}