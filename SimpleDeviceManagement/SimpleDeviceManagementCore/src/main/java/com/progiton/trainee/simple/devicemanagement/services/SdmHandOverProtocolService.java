package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.requests.SdmHandOverProtocolRequest;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;

import java.util.List;

public interface SdmHandOverProtocolService {
    List<SdmHandOverProtocolEntity> getAllHandOverProtocols();
    SdmHandOverProtocolEntity getHandOverProtocolById(Long id);
    
    
    
    SdmHandOverProtocolEntity saveHandOverProtocol(SdmHandOverProtocolRequest request);

    
    
    void deleteHandOverProtocol(Long id);
    
    
    
    SdmHandOverProtocolEntity updateHandOverProtocol(Long id, SdmHandOverProtocolEntity entity);
    SdmHandOverProtocolEntity confirmHandOverProtocol(Long id);
    List<SdmHandOverProtocolEntity> getHandOverProtocolsByReceiverUsername(String username);
    List<SdmHandOverProtocolEntity> getByDeviceSerialNumber(String serialNumber);
    SdmHandOverProtocolEntity confirmByDeviceSerialNumber(String serialNumber);
//    HandOverProtocolEntity saveHandOverProtocol(HandOverProtoco dto);
}
