package com.progiton.trainee.test.simple.devicemanagement.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(
        classes = com.progiton.trainee.simple.devicemanagement.app.SimpleDeviceManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CucumberSpringConfig {
    // This class bootstraps Spring Boot for Cucumber tests
    // - Loads full application context
    // - Uses test database (from application-test.properties)
    // - Makes MockMvc available for API testing
}
