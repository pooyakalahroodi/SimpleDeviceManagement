package com.progiton.trainee.simple.devicemanagement.model.to;

import com.progiton.trainee.simple.devicemanagement.model.SdmUser;

import java.util.List;


public class SdmUserTo implements SdmUser<SdmDeviceTo> {

	    private String username;
	    private String name;
		private String surname;
	    private Boolean enabled;
	    private SdmDepartmentTo department; // Department name, not ID
	    private List<SdmDeviceTo> devices; // Device descriptions, not IDs or objects


    // Constructors
	    public SdmUserTo() {
	        super();
	    }

	    public SdmUserTo(String username, String name, String surname,Boolean enabled, SdmDepartmentTo department, List<SdmDeviceTo> devices) {
	        this.username = username;
	        this.name = name;
	        this.surname = surname;
	        this.enabled = enabled;
	        this.department = department;
	        this.devices = devices;
	    }

	    // Getters and Setters
	    @Override
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(final String username) {
	        this.username = username;
	    }

	    @Override
	    public String getName() {
	        return name;
	    }

	    public void setName(final String name) {
	        this.name = name;
	    }
	    
		public void setSurname(final String surname) {
			this.surname = surname;
		}
		
		@Override
		public String getSurname() {
			return surname;
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
