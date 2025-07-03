package com.progiton.trainee.simple.devicemanagement.model.requests;

import jakarta.validation.constraints.NotBlank;

public class SdmCreateUserRequest {
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Name is required")
    private String surname;
    
    private Boolean enabled = true;
    
    private String departmentName;    
    // Constructors
    
    public SdmCreateUserRequest() {}
    
    public SdmCreateUserRequest(String username, String name, String surname, Boolean enabled, String departmentName) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.enabled = enabled;
        this.departmentName = departmentName;
    }
    
    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentId(String departmentName) { this.departmentName = departmentName; }


}
