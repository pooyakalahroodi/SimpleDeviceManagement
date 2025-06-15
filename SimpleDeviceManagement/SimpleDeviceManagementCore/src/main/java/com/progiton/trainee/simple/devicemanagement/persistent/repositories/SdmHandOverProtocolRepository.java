package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmHandOverProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdmHandOverProtocolRepository extends JpaRepository<SdmHandOverProtocolEntity, Long> {

    // Optional: Find all handovers for a specific device
	List<SdmHandOverProtocolEntity> findAllByDevice_SerialNumber(String serialNumber);

    // Optional: Find all handovers performed by a specific user
    List<SdmHandOverProtocolEntity> findByPerformedBy_Username(String username);

    // Optional: Find all handovers received by a specific user
    List<SdmHandOverProtocolEntity> findByReceiver_Username(String username);

    // Optional: Find all unconfirmed protocols
    List<SdmHandOverProtocolEntity> findByIsConfirmedFalse();
}
