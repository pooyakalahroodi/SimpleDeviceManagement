package com.progiton.trainee.simple.devicemanagement.mapper;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;
import com.progiton.trainee.simple.devicemanagement.model.to.SdmHandOverProtocolTo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SdmHandOverProtocolMapper {

    // === ENTITY → TO ===
    @Mapping(source = "device.serialNumber", target = "deviceSerialNumber")
    @Mapping(source = "receiver.username", target = "receiverUsername")
    @Mapping(source = "performedBy.username", target = "performedByUsername")
    @Mapping(source = "sdmActionType", target = "actionType")
    SdmHandOverProtocolTo toTo(SdmHandOverProtocolEntity entity);

    // === TO → ENTITY ===
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "performedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "sdmActionType", target = "actionType")
    SdmHandOverProtocolEntity toEntity(SdmHandOverProtocolTo dto);

    // === LIST MAPPINGS ===
    List<SdmHandOverProtocolTo> toToList(List<SdmHandOverProtocolEntity> entities);
    List<SdmHandOverProtocolEntity> toEntityList(List<SdmHandOverProtocolTo> dtos);
}
