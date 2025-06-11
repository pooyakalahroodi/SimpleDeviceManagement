package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.progiton.trainee.simple.devicemanagement.model.HandOverProtocol;

import jakarta.persistence.*;

@EnableJpaAuditing
@Entity
@Table(name = "handover_protocols")
@EntityListeners(AuditingEntityListener.class)
public class HandOverProtocolEntity implements HandOverProtocol {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity device;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", nullable = false)
    private UserEntity receiver;

    @ManyToOne
    @JoinColumn(name = "performed_by_user_id", nullable = false)
    private UserEntity performedBy;

    @Column(name = "handover_date", nullable = false)
    private LocalDateTime handoverDate;

    @Column(length = 500)
    private String comments;

    //TODO (LR) was ist mit beide Felder unten los. Warum sind die nicht in DB
    // Wenn ein Feld nicht persistier werden soll, soll als Transient bezeichnet werden.
    private Boolean isConfirmed = false;

    private LocalDateTime confirmedAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    
    
    public HandOverProtocolEntity() {
        // Default constructor (JPA needs this)
    }

    public HandOverProtocolEntity(Long id, DeviceEntity device, UserEntity receiver, UserEntity performedBy,
                                  LocalDateTime handoverDate, String comments, Boolean isConfirmed,
                                  LocalDateTime confirmedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.device = device;
        this.receiver = receiver;
        this.performedBy = performedBy;
        this.handoverDate = handoverDate;
        this.comments = comments;
        this.isConfirmed = isConfirmed;
        this.confirmedAt = confirmedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    
 // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public DeviceEntity getDevice() { return device; }
    public void setDevice(DeviceEntity device) { this.device = device; }

    public UserEntity getReceiver() { return receiver; }
    public void setReceiver(UserEntity receiver) { this.receiver = receiver; }
    
    public UserEntity getPerformedBy() { return performedBy; }
    public void setPerformedBy(UserEntity performedBy) { this.performedBy = performedBy; }

    // === Interface Getters for TO projection ===
    @Override
    public String getDeviceSerialNumber() {
        return device != null ? device.getSerialNumber() : null;
    }

    @Override
    public String getReceiverUsername() {
        return receiver != null ? receiver.getUsername() : null;
    }

    @Override
    public String getPerformedByUsername() {
        return performedBy != null ? performedBy.getUsername() : null;
    }


	@Override
	public LocalDateTime getHandoverDate() {
		return handoverDate;
	}
	
	public void setHandoverDate(LocalDateTime handoverDate) { this.handoverDate = handoverDate; }

	@Override
	public String getComments() {
		return comments;
	}
	
    public void setComments(String comments) { this.comments = comments; }


	@Override
	public Boolean getIsConfirmed() {
		return isConfirmed;
	}
	

    public void setIsConfirmed(Boolean isConfirmed) { this.isConfirmed = isConfirmed; }


	@Override
	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}
	
    public void setConfirmedAt(LocalDateTime confirmedAt) { this.confirmedAt = confirmedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Getters and setters
}
