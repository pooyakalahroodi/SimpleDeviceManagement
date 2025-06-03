package com.progiton.trainee.simple.devicemanagement.to;

import lombok.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTo {
    private Long id;
    private String username;
    private String name;
    private Long departmentId; // We expose only the ID of the department
    private List<Long> deviceIds; // We expose only the IDs of assigned devices
    private Set<Long> roleIds; // We expose only the IDs of roles
    // Note: password is not exposed in DTO for security reasons
}