package com.progiton.trainee.simple.devicemanagement.services;

import com.progiton.trainee.simple.devicemanagement.model.requests.HandOverProtocolRequest;
import com.progiton.trainee.simple.devicemanagement.persistent.model.HandOverProtocolEntity;

import java.util.List;

public interface HandOverProtocolService {
    List<HandOverProtocolEntity> getAllHandOverProtocols();
    HandOverProtocolEntity getHandOverProtocolById(Long id);
    
    
    
    HandOverProtocolEntity saveHandOverProtocol(HandOverProtocolRequest request);

    
    
    void deleteHandOverProtocol(Long id);
    
    
    
    HandOverProtocolEntity updateHandOverProtocol(Long id, HandOverProtocolEntity entity);
    HandOverProtocolEntity confirmHandOverProtocol(Long id);
    List<HandOverProtocolEntity> getHandOverProtocolsByReceiverUsername(String username);
    List<HandOverProtocolEntity> getByDeviceSerialNumber(String serialNumber);
    HandOverProtocolEntity confirmByDeviceSerialNumber(String serialNumber);
//    HandOverProtocolEntity saveHandOverProtocol(HandOverProtoco dto);
}
