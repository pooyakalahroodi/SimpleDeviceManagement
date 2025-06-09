package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.progiton.trainee.simple.devicemanagement.model.enums.*;
import com.progiton.trainee.simple.devicemanagement.model.Device;

import jakarta.persistence.*;


@EnableJpaAuditing  // Add this to your main application class or config
@Entity
@EntityListeners(AuditingEntityListener.class)
public class DeviceEntity implements Device {
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
    private DeviceStatus status;
    
    // Auditing fields =====================
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    // ====================================

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity assignedTo;

    public DeviceEntity() {
    }

    public DeviceEntity(Long id, String name, String type, String serialNumber,
                        String manufacturer, String location, LocalDate purchaseDate,
                        DeviceStatus status, UserEntity assignedTo,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.location = location;
        this.purchaseDate = purchaseDate;
        this.status = status;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    public String getStatus() {
    	return status != null ? status.name() : null;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }


    public UserEntity getAssignedTo() {
		return assignedTo;

	}
    
    public void setAssignedTo(UserEntity assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	@Override
	public String getAssignedToUsername() {
		
	    if (assignedTo != null) {
	        return assignedTo.getUsername();
	    }
	    return null;
	}

}

