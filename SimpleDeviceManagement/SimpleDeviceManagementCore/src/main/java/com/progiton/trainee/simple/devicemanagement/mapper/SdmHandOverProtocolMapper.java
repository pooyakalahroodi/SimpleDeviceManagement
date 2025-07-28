package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;

@Mapper(componentModel = "spring")
public interface SdmHandOverProtocolMapper {

	// === ENTITY → TO ===
	@Mapping(source = "device.serialNumber", target = "deviceSerialNumber")
	@Mapping(source = "receiver.username", target = "receiverUsername")
	@Mapping(source = "performedBy.username", target = "performedByUsername")
//	@Mapping(target = "actionType", source = "actionType", qualifiedByName = "mapActionType")
	SdmHandOverProtocolTo toTo(SdmHandOverProtocolEntity entity);

	// === TO → ENTITY ===
	@Mapping(target = "device", ignore = true)
	@Mapping(target = "receiver", ignore = true)
	@Mapping(target = "performedBy", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
//	@Mapping(source = "actionType", target = "actionType")
	SdmHandOverProtocolEntity toEntity(SdmHandOverProtocolTo to);

	// === LIST MAPPINGS ===
	@IterableMapping(elementTargetType = SdmHandOverProtocolTo.class)
	List<SdmHandOverProtocolTo> toToList(List<SdmHandOverProtocolEntity> entities);

	@IterableMapping(elementTargetType = SdmDeviceEntity.class)
	List<SdmDeviceEntity> toEntityList(List<SdmHandOverProtocolTo> dtos);
}
