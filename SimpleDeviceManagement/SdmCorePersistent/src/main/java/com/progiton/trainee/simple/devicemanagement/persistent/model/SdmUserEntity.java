package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.util.List;
import java.util.UUID;

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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class SdmUserEntity extends SdmBaseEntity<Long> implements SdmUser<SdmDeviceEntity> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Option 1: Database-agnostic (recommended)
	@Column(name = "user_id", unique = true)
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID userId;

	@Column(name = "email_address", length = 50,unique = true, nullable = false)
	private String emailAddress;

	private boolean enabled;

	private String name;

	private String surname;

	@OneToMany(mappedBy = "user")
	private List<SdmDeviceEntity> sdmDevices;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private SdmDepartmentEntity department;

	// Constructors
	public SdmUserEntity() {
	}

	public SdmUserEntity(String emailAddress, String name, String password) {
		this.emailAddress = emailAddress;
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
	public UUID getUserId() {
		return userId;
	}

	public UUID setUserId(UUID userId){this.userId= this.userId;
        return userId;
    }

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
		return department;
	}

	public void setDepartment(SdmDepartmentEntity sdmDepartmentEntity) {
		this.department = sdmDepartmentEntity;
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
