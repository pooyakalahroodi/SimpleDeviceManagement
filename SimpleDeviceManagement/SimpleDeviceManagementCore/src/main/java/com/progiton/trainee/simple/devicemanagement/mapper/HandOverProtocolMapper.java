package com.progiton.trainee.simple.devicemanagement.mapper;

import com.progiton.trainee.simple.devicemanagement.persistent.model.HandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.model.to.HandOverProtocolTo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HandOverProtocolMapper {

    // === ENTITY → TO ===
    @Mapping(source = "device.serialNumber", target = "deviceSerialNumber")
    @Mapping(source = "receiver.username", target = "receiverUsername")
    @Mapping(source = "performedBy.username", target = "performedByUsername")
    HandOverProtocolTo toTo(HandOverProtocolEntity entity);

    // === TO → ENTITY ===
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "performedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HandOverProtocolEntity toEntity(HandOverProtocolTo dto);

    // === LIST MAPPINGS ===
    List<HandOverProtocolTo> toToList(List<HandOverProtocolEntity> entities);
    List<HandOverProtocolEntity> toEntityList(List<HandOverProtocolTo> dtos);
}
