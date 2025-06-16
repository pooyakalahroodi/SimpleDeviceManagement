package com.progiton.trainee.simple.devicemanagement.config;

import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmRoleEntity;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmPermission;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(1) // Ensures this runs before TestDataInitializer
@RequiredArgsConstructor
@Slf4j
	public class RoleInitializer implements CommandLineRunner {@Override
	public void run(String... args) throws Exception {
		
	}

}
