package com.progiton.trainee.simple.devicemanagement.persistent.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmDeviceEntity;

@Repository
public interface SdmDeviceRepository extends JpaRepository<SdmDeviceEntity, Long> {

	Optional<SdmDeviceEntity> findBySerialNumber(String serialNumber);

	boolean existsBySerialNumber(String serialNumber);

}
