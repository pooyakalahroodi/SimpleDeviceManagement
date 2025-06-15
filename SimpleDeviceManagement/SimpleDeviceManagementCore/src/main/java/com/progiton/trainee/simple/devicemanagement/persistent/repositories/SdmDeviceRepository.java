package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdmDeviceRepository extends JpaRepository<SdmDeviceEntity, Long> {

	SdmDeviceEntity findBySerialNumber(String serialNumber);

}
