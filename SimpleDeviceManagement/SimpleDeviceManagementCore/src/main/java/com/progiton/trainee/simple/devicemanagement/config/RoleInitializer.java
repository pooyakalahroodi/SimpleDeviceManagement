package com.progiton.trainee.simple.devicemanagement.config;

import com.progiton.trainee.simple.devicemanagement.model.RoleEntity;
import com.progiton.trainee.simple.devicemanagement.model.enums.Permission;
import com.progiton.trainee.simple.devicemanagement.repositories.RoleRepository;
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
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.count() > 0) {
            log.info("Roles already exist — skipping initialization.");
            return;
        }

        log.info("🔐 Seeding default roles with permissions...");

        roleRepository.save(RoleEntity.builder()
            .name("IT_ADMIN")
            .description("IT Administrator - full system access")
            .permissions(Set.of(
                Permission.DEVICE_CREATE, Permission.DEVICE_READ, Permission.DEVICE_UPDATE, Permission.DEVICE_DELETE, Permission.DEVICE_ASSIGN,
                Permission.USER_CREATE, Permission.USER_READ, Permission.USER_UPDATE, Permission.USER_DELETE,
                Permission.REPORT_VIEW, Permission.REPORT_DEPARTMENT,
                Permission.POLICY_CREATE, Permission.POLICY_UPDATE, Permission.POLICY_VIEW,
                Permission.SYSTEM_ADMIN
            ))
            .build());

        roleRepository.save(RoleEntity.builder()
            .name("IT_STAFF")
            .description("IT Staff - device access and user visibility")
            .permissions(Set.of(
                Permission.DEVICE_READ, Permission.DEVICE_UPDATE, Permission.DEVICE_ASSIGN,
                Permission.USER_READ
            ))
            .build());

        roleRepository.save(RoleEntity.builder()
            .name("IT_MANAGER")
            .description("IT Manager - reporting and assignments")
            .permissions(Set.of(
                Permission.DEVICE_READ, Permission.DEVICE_ASSIGN,
                Permission.REPORT_VIEW
            ))
            .build());

        roleRepository.save(RoleEntity.builder()
            .name("HR_STAFF")
            .description("HR Staff - user and department reporting")
            .permissions(Set.of(
                Permission.USER_READ, Permission.REPORT_DEPARTMENT
            ))
            .build());

        roleRepository.save(RoleEntity.builder()
            .name("EMPLOYEE")
            .description("Employee - limited read access")
            .permissions(Set.of(
                Permission.DEVICE_READ, Permission.USER_READ
            ))
            .build());

        log.info("✅ Roles and permissions seeded successfully.");
    }
}
