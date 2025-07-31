package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.util.List;

import com.progiton.trainee.simple.devicemanagement.model.SdmDepartment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class SdmDepartmentEntity extends SdmBaseEntity<Long> implements SdmDepartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "department")
	private List<SdmUserEntity> sdmUserEntity;

	// No-args constructor
	public SdmDepartmentEntity() {
	}

	// All-args constructor
	public SdmDepartmentEntity(final Long id, final String name, final List<SdmUserEntity> sdmUserEntity) {
		this.id = id;
		this.name = name;
		this.sdmUserEntity = sdmUserEntity;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SdmUserEntity> getUserEntity() {
		return sdmUserEntity;
	}

	public void setUserEntity(List<SdmUserEntity> sdmUserEntity) {
		this.sdmUserEntity = sdmUserEntity;
	}

}
