package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.Instant;
import java.util.UUID;

import com.progiton.trainee.simple.devicemanagement.model.SdmHandOverProtocol;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SdmHandOverProtocolTo implements SdmHandOverProtocol {

	@NotBlank
	@Size(max = 50)
	private String deviceSerialNumber;

	@NotNull
	private UUID receiverUserId;

	@NotNull
	private UUID performedByUserId;

	@NotNull
	private SdmActionType actionType;

	@NotNull
	private Instant handoverDate;

	@Size(max = 500)
	private String comments;

	@NotNull
	private Boolean isConfirmed;

	private Instant confirmedAt;

	// Constructors
	public SdmHandOverProtocolTo() {
		super();

	}

	public SdmHandOverProtocolTo(String deviceSerialNumber, UUID receiverUserId, UUID performedByUserId,
								 SdmActionType actionType, Instant handoverDate, String comments, Boolean isConfirmed, Instant confirmedAt) {

		this.deviceSerialNumber = deviceSerialNumber;
		this.receiverUserId = receiverUserId;
		this.performedByUserId = performedByUserId;
		this.actionType = actionType;
		this.handoverDate = handoverDate;
		this.comments = comments;
		this.isConfirmed = isConfirmed;
		this.confirmedAt = confirmedAt;
	}

	@Override
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	@Override
	public UUID getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(UUID receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	@Override
	public UUID getPerformedByUserId() {
		return performedByUserId;
	}

	public void setPerformedByUserId(UUID performedByUserId) {
		this.performedByUserId = performedByUserId;
	}

	@Override
	public SdmActionType getActionType() {
		return this.actionType;
	}

	public void setActionType(SdmActionType sdmActionType) {
		this.actionType = sdmActionType;
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
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

}
