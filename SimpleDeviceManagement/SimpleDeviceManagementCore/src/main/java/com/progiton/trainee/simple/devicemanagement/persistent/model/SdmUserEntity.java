package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.SdmUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class SdmUserEntity extends SdmBaseEntity<Long> implements SdmUser<SdmDeviceEntity> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	private boolean enabled;

	private String name;

	private String surname;

	@OneToMany(mappedBy = "user")
	private List<SdmDeviceEntity> sdmDevices;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private SdmDepartmentEntity sdmDepartment;

	// Constructors
	public SdmUserEntity() {
	}

	public SdmUserEntity(String username, String name, String password) {
		this.username = username;
		this.name = name;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public SdmDepartmentEntity getDepartment() {
		return sdmDepartment;
	}

	public void setDepartment(SdmDepartmentEntity sdmDepartmentEntity) {
		this.sdmDepartment = sdmDepartmentEntity;
	}

	@Override
	public List<SdmDeviceEntity> getDevices() {
		return sdmDevices;
	}

	public void setDevices(final List<SdmDeviceEntity> sdmDevice) {
		this.sdmDevices = sdmDevice;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}
