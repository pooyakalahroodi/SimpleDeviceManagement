package com.progiton.trainee.simple.devicemanagement.model.to;

import com.progiton.trainee.simple.devicemanagement.model.User;
import lombok.*;
import java.util.List;
import java.util.Set;

public class UserTo implements User {

    private Long id;
    private String username;
    private String name;
    private Long departmentId; // We expose only the ID of the department
    private List<Long> deviceIds; // We expose only the IDs of assigned devices
    private Set<Long> roleIds; // We expose only the IDs of roles
    // Note: password is not exposed in DTO for security reasons
}