package com.progiton.trainee.simple.devicemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.progiton.trainee.simple.devicemanagement.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.to.DeviceTo;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
	

	@Mapping(source = "assignedTo.name", target = "assignedToName")
	DeviceTo toTo(DeviceEntity Device);
    
    DeviceEntity toEntity(DeviceTo dto);
    
    
	List<DeviceTo> toToList(List<DeviceEntity> devices);
    List<DeviceEntity> toEntityList(List<DeviceTo> dtos);

	
	
}
