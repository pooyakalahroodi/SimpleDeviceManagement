package com.progiton.trainee.simple.devicemanagement.model.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.progiton.trainee.simple.devicemanagement.model.SdmUser;

import java.time.Instant;
import java.util.List;
import java.util.Set;


public class SdmUserTo extends SdmBaseTO implements SdmUser {

	    private String username;
	    private String name;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Only accept password in requests, don't return it
	    private String password;
	    
	    private Boolean enabled;
	    private String department; // Department name, not ID
	    private List<String> devices; // Device descriptions, not IDs or objects
	    private Instant createdAt;
	    private Instant updatedAt;

	    // Constructors
	    public SdmUserTo() {
	        super();
	    }


	    public SdmUserTo(String username, String name, Boolean enabled, String department, List<String> devices, Instant createdAt, Instant updatedAt) {
	        super(createdAt, updatedAt);
	        this.username = username;
	        this.name = name;
	        this.enabled = enabled;
	        this.department = department;
	        this.devices = devices;

	    }

	    // Getters and Setters
	    @Override
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    @Override
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    @Override
	    public Boolean getEnabled() {
	        return enabled;
	    }

	    public void setEnabled(Boolean enabled) {
	        this.enabled = enabled;
	    }

	    @Override
	    public String getDepartment() {
	        return department;
	    }

	    public void setDepartment(String department) {
	        this.department = department;
	    }

	    @Override
	    public List<String> getDevices() {
	        return devices;
	    }

	    public void setDevices(List<String> devices) {
	        this.devices = devices;
	    }
	    
	    
}
