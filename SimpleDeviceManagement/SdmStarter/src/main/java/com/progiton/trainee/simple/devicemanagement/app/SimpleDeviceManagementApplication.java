package com.progiton.trainee.simple.devicemanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories(basePackages = "com.progiton.trainee.simple.devicemanagement.persistent.repositories")
@SpringBootApplication(scanBasePackages = {
    "com.progiton.trainee.simple.devicemanagement"})
@EnableJpaAuditing
@ComponentScan(basePackages = "com.progiton.trainee")
@EntityScan(basePackages = "com.progiton.trainee.simple.devicemanagement.persistent.model")
public class SimpleDeviceManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleDeviceManagementApplication.class, args);
    }
}
