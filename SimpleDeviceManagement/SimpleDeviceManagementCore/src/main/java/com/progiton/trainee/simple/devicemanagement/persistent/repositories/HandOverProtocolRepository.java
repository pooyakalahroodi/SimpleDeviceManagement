package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import com.progiton.trainee.simple.devicemanagement.persistent.model.HandOverProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandOverProtocolRepository extends JpaRepository<HandOverProtocolEntity, Long> {

    // Optional: Find all handovers for a specific device
	List<HandOverProtocolEntity> findAllByDevice_SerialNumber(String serialNumber);

    // Optional: Find all handovers performed by a specific user
    List<HandOverProtocolEntity> findByPerformedBy_Username(String username);

    // Optional: Find all handovers received by a specific user
    List<HandOverProtocolEntity> findByReceiver_Username(String username);

    // Optional: Find all unconfirmed protocols
    List<HandOverProtocolEntity> findByIsConfirmedFalse();
}
