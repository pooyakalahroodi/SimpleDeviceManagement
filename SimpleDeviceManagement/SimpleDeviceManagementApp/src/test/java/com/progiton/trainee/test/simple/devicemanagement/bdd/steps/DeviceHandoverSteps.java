package com.progiton.trainee.test.simple.devicemanagement.bdd.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeviceHandoverSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Counters for auto-incrementing IDs
    private long departmentIdCounter = 1;
    private long userIdCounter = 1;
    private long deviceIdCounter = 1;
    private long protocolIdCounter = 1;

    private final Map<String, Long> departmentIds = new HashMap<>();
    private final Map<String, Long> userIds = new HashMap<>();
    private final Map<String, Long> deviceIds = new HashMap<>();
    private final Map<String, Long> protocolIds = new HashMap<>();

    @Given("a department exists with name {string}")
    public void aDepartmentExists(String departmentName) throws Exception {
        String requestBody = String.format("{\"name\": \"%s\"}", departmentName);

        mockMvc.perform(post("/api/departments")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        Long id = departmentIdCounter++;
        departmentIds.put(departmentName, id);

        System.out.println("✓ Created department: " + departmentName + " (ID: " + id + ")");
    }

    @And("a user exists with username {string} in department {string}")
    public void aUserExists(String username, String departmentName) throws Exception {
        Long deptId = departmentIds.get(departmentName);

        if (deptId == null) {
            throw new RuntimeException("Department ID not found for: " + departmentName);
        }

        // Based on SdmUserController - it expects nested department object
        String requestBody = String.format("""
            {
              "username": "%s",
              "name": "Test",
              "surname": "User",
              "enabled": true,
              "department": {
                "id": %d,
                "name": "%s"
              }
            }
            """, username, deptId, departmentName);

        System.out.println("=== Creating User ===");
        System.out.println("Request: " + requestBody);

        mockMvc.perform(post("/api/users")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        Long id = userIdCounter++;
        userIds.put(username, id);

        System.out.println("✓ Created user: " + username + " (ID: " + id + ")");
    }

    @And("a device exists with serial number {string}")
    public void aDeviceExists(String serialNumber) throws Exception {
        String today = LocalDate.now().toString();
        // Based on SdmDeviceController
        String requestBody = String.format("""
            {
              "name": "Test Device",
              "serialNumber": "%s",
              "type": "Laptop",
              "status": "ACTIVE",
              "manufacturer": "Dell",
              "location": "Office",
              "purchaseDate": "%s"
            }
            """, serialNumber, today);

        mockMvc.perform(post("/api/devices")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())  // ← Accept any 2xx status
                .andReturn();

        Long id = deviceIdCounter++;
        deviceIds.put(serialNumber, id);

        System.out.println("✓ Created device: " + serialNumber + " (ID: " + id + ")");
    }

    @When("I create a handover protocol for device {string} to user {string}")
    public void iCreateHandoverProtocol(String serialNumber, String username) throws Exception {
        Long deviceId = deviceIds.get(serialNumber);
        Long receiverId = userIds.get(username);

        if (deviceId == null) {
            throw new RuntimeException("Device ID not found for: " + serialNumber);
        }
        if (receiverId == null) {
            throw new RuntimeException("User ID not found for: " + username);
        }

        // Same user performs and receives for test simplicity
        Long performedById = receiverId;
        String performedByUsername = username;  // Same as receiver in tests

        String handoverDate = Instant.now().toString();

        String requestBody = String.format("""
        {
          "device": {
            "id": %d
          },
          "receiver": {
            "id": %d
          },
          "performedBy": {
            "id": %d
          },
          "deviceSerialNumber": "%s",
          "receiverUsername": "%s",
          "performedByUsername": "%s",
          "actionType": "HANDOVER",
          "handoverDate": "%s",
          "description": "Test handover protocol",
          "isConfirmed": false
        }
        """, deviceId, receiverId, performedById,
                serialNumber, username, performedByUsername, handoverDate);

        System.out.println("=== Creating Handover Protocol ===");
        System.out.println("Device: " + serialNumber + " (ID: " + deviceId + ")");
        System.out.println("Receiver: " + username + " (ID: " + receiverId + ")");
        System.out.println("Performed By: " + performedByUsername + " (ID: " + performedById + ")");

        mockMvc.perform(post("/api/handover-protocols")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        Long id = protocolIdCounter++;
        protocolIds.put(serialNumber, id);

        System.out.println("✓ Created handover protocol (ID: " + id + ")");
    }



    @And("I confirm the handover protocol for device {string}")
    public void iConfirmHandoverProtocol(String serialNumber) throws Exception {
        // Use device serial number in URL, not protocol ID
        String confirmUrl = "/api/handover-protocols/device/" + serialNumber + "/confirm";

        System.out.println("=== Confirming Protocol ===");
        System.out.println("Device Serial: " + serialNumber);
        System.out.println("Confirm URL: " + confirmUrl);

        mockMvc.perform(put(confirmUrl)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());

        System.out.println("✓ Confirmed handover protocol for device: " + serialNumber);
    }


    @Then("user {string} should have device {string} in their device list")
    public void userShouldHaveDevice(String username, String serialNumber) throws Exception {
        Long userId = userIds.get(username);

        MvcResult result = mockMvc.perform(get("/api/users/username/" + username)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("User information" + result.getResponse().getContentAsString());

        Map<String, Object> user = objectMapper.readValue(
                result.getResponse().getContentAsString(), Map.class);

        List<Map<String, Object>> devices = (List<Map<String, Object>>) user.get("devices");

        if (devices == null || devices.isEmpty()) {
            throw new AssertionError("User has no devices");
        }

        boolean hasDevice = devices.stream()
                .anyMatch(device -> serialNumber.equals(device.get("serialNumber")));

        assertThat(hasDevice)
                .as("User %s should have device %s", username, serialNumber)
                .isTrue();

        System.out.println("✓ Verified: User " + username + " has device " + serialNumber);
    }

    @Then("user {string} should have {int} devices")
    public void userShouldHaveDevices(String username, int expectedCount) throws Exception {
        Long userId = userIds.get(username);

        MvcResult result = mockMvc.perform(get("/api/users/username/" + username)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> user = objectMapper.readValue(
                result.getResponse().getContentAsString(), Map.class);

        List<Map<String, Object>> devices = (List<Map<String, Object>>) user.get("devices");

        if (devices == null) {
            devices = List.of();
        }

        assertThat(devices).hasSize(expectedCount);

        System.out.println("✓ Verified: User " + username + " has " + expectedCount + " devices");
    }
}
