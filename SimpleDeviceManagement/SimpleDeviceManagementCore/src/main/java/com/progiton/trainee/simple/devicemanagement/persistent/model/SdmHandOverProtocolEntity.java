package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.time.Instant;

import com.progiton.trainee.simple.devicemanagement.model.SdmHandOverProtocol;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;

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
@Table(name = "handover_protocols")
public class SdmHandOverProtocolEntity extends SdmBaseEntity<Long> implements SdmHandOverProtocol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "device_id", nullable = false)
	private SdmDeviceEntity device;

	@ManyToOne
	@JoinColumn(name = "receiver_user_id", nullable = false)
	private SdmUserEntity receiver;

	@Enumerated(EnumType.STRING)
	@Column(name = "action_type", nullable = false)
	private SdmActionType actionType;

	@ManyToOne
	@JoinColumn(name = "performed_by_user_id", nullable = false)
	private SdmUserEntity performedBy;

	@Column(name = "handover_date", nullable = false)
	private Instant handoverDate;

	@Column(length = 500)
	private String description;

	@Column(name = "is_confirmed")
	private boolean isConfirmed = false;

	@Column(name = "confirmed_at")
	private Instant confirmedAt;

	public SdmHandOverProtocolEntity() {
		// Default constructor (JPA needs this)
	}

	public SdmHandOverProtocolEntity(Long id, SdmDeviceEntity device, SdmUserEntity receiver, SdmUserEntity performedBy,
			SdmActionType actionType, Instant handoverDate, String comments, Boolean isConfirmed, Instant confirmedAt) {
		this.id = id;
		this.device = device;
		this.receiver = receiver;
		this.performedBy = performedBy;
		this.handoverDate = handoverDate;
		this.actionType = actionType;
		this.description = comments;
		this.isConfirmed = isConfirmed;
		this.confirmedAt = confirmedAt;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SdmDeviceEntity getDevice() {
		return device;
	}

	public void setDevice(SdmDeviceEntity device) {
		this.device = device;
	}

	public SdmUserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(SdmUserEntity receiver) {
		this.receiver = receiver;
	}

	public SdmUserEntity getPerformedBy() {
		return performedBy;
	}

	public void setPerformedBy(SdmUserEntity performedBy) {
		this.performedBy = performedBy;
	}

	// === Interface Getters for TO projection ===

	public SdmActionType getSdmActionType() {
		return this.actionType;
	}

	public void setActionType(SdmActionType sdmActionType) {
		this.actionType = sdmActionType;
	}

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
	public Instant getHandoverDate() {
		return handoverDate;
	}

	public void setHandoverDate(Instant handoverDate) {
		this.handoverDate = handoverDate;
	}

	@Override
	public String getComments() {
		return description;
	}

	public void setComments(String comments) {
		this.description = comments;
	}

	@Override
	public boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	@Override
	public Instant getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(Instant confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	@Override
	public String getActionType() {
		return actionType != null ? actionType.name() : null;
	}

}
