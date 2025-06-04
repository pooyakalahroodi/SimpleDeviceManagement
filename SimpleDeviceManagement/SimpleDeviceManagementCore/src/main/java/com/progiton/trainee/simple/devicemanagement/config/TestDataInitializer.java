package com.progiton.trainee.simple.devicemanagement.config;

import com.progiton.trainee.simple.devicemanagement.model.enums.DeviceStatus;
import com.progiton.trainee.simple.devicemanagement.persistent.model.DeviceEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.RoleEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.model.UserEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.DeviceRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.RoleRepository;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class TestDataInitializer implements CommandLineRunner {

    // private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Random RANDOM = new Random();

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0 || deviceRepository.count() > 0) {
            log.info("Users or devices already exist — skipping test data initialization.");
            return;
        }

        log.info("📦 Seeding test users and devices...");

        // List<Department> departments = seedDepartments();
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<UserEntity> userEntities = seedUsers(/*departments,*/ roleEntities);
        seedDevices(/*departments,*/ userEntities);

        log.info("✅ Test data seeding complete.");
    }

    // private List<Department> seedDepartments() {
    //     List<Department> departments = List.of(
    //         Department.builder().name("IT").description("IT Dept").build(),
    //         Department.builder().name("HR").description("HR Dept").build(),
    //         Department.builder().name("Finance").description("Finance Dept").build()
    //     );
    //     departmentRepository.saveAll(departments);
    //     log.info("✓ Departments seeded");
    //     return departments;
    // }

    private List<UserEntity> seedUsers(/*List<Department> departments,*/ List<RoleEntity> roleEntities) {
        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            // Department adept = getRandomElement(departments);
            Set<RoleEntity> userRoles = Set.of(getRandomElement(roleEntities));

            UserEntity userEntity = UserEntity.builder()
                .username("user" + i)
                .name("Test User " + i)
                .enabled(true)
                // .department(dept)
                .roleEntities(userRoles)
                .password(passwordEncoder.encode("password" + i))
                .build();

            userEntities.add(userRepository.save(userEntity));
        }
        log.info("✓ Test users seeded");
        return userEntities;
    }

    private void seedDevices(/*List<Department> departments,*/ List<UserEntity> userEntities) {
        for (int i = 1; i <= 5; i++) {
            // Department adept = getRandomElement(departments);
            UserEntity assignedTo = (i % 2 == 0) ? getRandomElement(userEntities) : null;

            DeviceEntity deviceEntity = DeviceEntity.builder()
                .serialNumber("DEV" + i)
                .type("Laptop")
                .manufacturer("BrandX")
                .location("Unknown")
                .purchaseDate(LocalDate.now().minusDays(i * 10))
                .status(DeviceStatus.values()[i % DeviceStatus.values().length])
                .assignedTo(assignedTo)
                // .department(adept)
                .build();

            deviceRepository.save(deviceEntity);
        }
        log.info("✓ Test devices seeded");
    }

    private <T> T getRandomElement(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
