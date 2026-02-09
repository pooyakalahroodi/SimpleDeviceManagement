package com.progiton.trainee.simple.devicemanagement.model.to;

import java.util.List;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.model.SdmUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SdmUserTo implements SdmUser<SdmDeviceTo> {

	private UUID userId;

	@NotBlank
	@Email
	@Size(min=4, max = 50)
	private String emailAddress;

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 100)
	private String surname;

	@NotNull
	private Boolean enabled;

	@NotNull
	@Valid
	private SdmDepartmentTo department;

	@Valid
	private List<SdmDeviceTo> devices;

	// Constructors
	public SdmUserTo() {
		super();
	}

	public SdmUserTo(UUID userId, String emailAddress, String name, String surname,
					 Boolean enabled, SdmDepartmentTo department, List<SdmDeviceTo> devices) {
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.name = name;
		this.surname = surname;
		this.enabled = enabled;
		this.department = department;
		this.devices = devices;
	}

	// ✅ FIX 1: Return userId
	@Override
	public UUID getUserId() {
		return userId;
	}

	// ✅ FIX 2: Set userId correctly (was setting emailAddress!)
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	// ✅ FIX 3: Add setEmailAddress
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public SdmDepartmentTo getDepartment() {
		return department;
	}

	public void setDepartment(SdmDepartmentTo department) {
		this.department = department;
	}

	@Override
	public List<SdmDeviceTo> getDevices() {
		return devices;
	}

	public void setDevices(List<SdmDeviceTo> devices) {
		this.devices = devices;
	}
}
