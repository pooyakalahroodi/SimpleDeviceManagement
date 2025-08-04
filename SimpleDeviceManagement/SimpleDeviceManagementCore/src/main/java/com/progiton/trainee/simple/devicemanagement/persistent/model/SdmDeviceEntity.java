package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.time.LocalDate;

import com.progiton.trainee.simple.devicemanagement.model.SdmDevice;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmDeviceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class SdmDeviceEntity extends SdmBaseEntity<Long> implements SdmDevice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; 

	@Column(nullable = false)
	private String type;

	@Column(nullable = false, unique = true)
	private String serialNumber;

	private String manufacturer;

	@Column(nullable = false)
	private String location;

	private LocalDate purchaseDate;


	@Enumerated(EnumType.STRING)
	private SdmDeviceStatus status;

	// TODO : make it optional (NPE safe)
	@ManyToOne
	@JoinColumn(name = "user_id")
	private SdmUserEntity user;

	public SdmDeviceEntity() {
	}

	public SdmDeviceEntity(Long id, String name, String type, String serialNumber, String manufacturer, String location,
			LocalDate purchaseDate, SdmDeviceStatus status, SdmUserEntity assignedTo) {

		this.id = id;
		this.name = name;
		this.type = type;
		this.serialNumber = serialNumber;
		this.manufacturer = manufacturer;
		this.location = location;
		this.purchaseDate = purchaseDate;
		this.status = status;
		this.user = assignedTo;

	}

	// Getters and Setters
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

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public SdmDeviceStatus getStatus() {
		return status != null ? status : null;
	}

	public void setStatus(SdmDeviceStatus status) {
		this.status = status;
	}

	public SdmUserEntity getUser() {
		return user;

	}

	public void setUser(SdmUserEntity assignedTo) {
		this.user = assignedTo;
	}

}
