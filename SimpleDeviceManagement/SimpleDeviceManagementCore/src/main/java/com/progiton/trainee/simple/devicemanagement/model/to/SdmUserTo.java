package com.progiton.trainee.simple.devicemanagement.model.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.progiton.trainee.simple.devicemanagement.model.SdmUser;

import java.time.Instant;
import java.util.List;
import java.util.Set;


public class SdmUserTo extends SdmBaseTO implements SdmUser<SdmDeviceTo> {

	    private String username;
	    private String name;
	    
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Only accept password in requests, don't return it
	    private String password;
	    
	    private Boolean enabled;
	    private SdmDepartmentTo department; // Department name, not ID
	    private List<SdmDeviceTo> devices; // Device descriptions, not IDs or objects
	    private Instant createdAt;
	    private Instant updatedAt;

	    // Constructors
	    public SdmUserTo() {
	        super();
	    }


	    public SdmUserTo(String username, String name, Boolean enabled, SdmDepartmentTo department, List<SdmDeviceTo> devices, Instant createdAt, Instant updatedAt) {
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

	    public void setEnabled(final Boolean enabled) {
	        this.enabled = enabled;
	    }

	    @Override
	    public SdmDepartmentTo getDepartment() {
	        return department;
	    }

	    public void setDepartment(final SdmDepartmentTo department) {
	        this.department = department;
	    }

	    @Override
	    public List<SdmDeviceTo> getDevices() {
	        return devices;
	    }

	    public void setDevices(final List<SdmDeviceTo> devices) {
	        this.devices = devices;
	    }
	    
	    
}
