package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmDeviceTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;

@Mapper(componentModel = "spring")
public interface SdmDeviceMapper {

	@Named("mapStatus")
	default SdmDeviceStatus mapStatus(String status) {
		return status != null ? SdmDeviceStatus.valueOf(status) : null;
	}

	// ENTITY -> DTO
	@Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
	SdmDeviceTo toTo(SdmDeviceEntity device);

	// DTO -> ENTITY
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "status", expression = "java(mapStatus(dto.getStatus()))")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	SdmDeviceEntity toEntity(SdmDeviceTo dto);

	List<SdmDeviceTo> toToList(List<SdmDeviceEntity> devices);

	List<SdmDeviceEntity> toEntityList(List<SdmDeviceTo> dtos);

}