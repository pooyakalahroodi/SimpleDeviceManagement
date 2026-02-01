package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.Instant;

import com.progiton.trainee.simple.devicemanagement.model.SdmHandOverProtocol;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SdmHandOverProtocolTo implements SdmHandOverProtocol {

	@NotBlank
	@Size(max = 50)
	private String deviceSerialNumber;

	@NotBlank
	@Size(max = 50)
	private String receiverUsername;

	@NotBlank
	@Size(max = 50)
	private String performedByUsername;

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

	public SdmHandOverProtocolTo(String deviceSerialNumber, String receiverUsername, String performedByUsername,
			SdmActionType actionType, Instant handoverDate, String comments, Boolean isConfirmed, Instant confirmedAt) {

		this.deviceSerialNumber = deviceSerialNumber;
		this.receiverUsername = receiverUsername;
		this.performedByUsername = performedByUsername;
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
	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	@Override
	public String getPerformedByUsername() {
		return performedByUsername;
	}

	public void setPerformedByUsername(String performedByUsername) {
		this.performedByUsername = performedByUsername;
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
