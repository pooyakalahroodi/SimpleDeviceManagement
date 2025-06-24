package com.progiton.trainee.simple.devicemanagement.model.requests;

import java.time.Instant;

import com.progiton.trainee.simple.devicemanagement.model.SdmHandOverProtocol;
import com.progiton.trainee.simple.devicemanagement.model.enums.SdmActionType;

/**
 * This class represents the input request sent from the client when creating a new handover protocol.
 * It implements HandOverProtocol for compatibility with shared interface, but is only meant for input.
 */
public class SdmHandOverProtocolRequest implements SdmHandOverProtocol {

    private String deviceSerialNumber;
    private String receiverUsername;
    private String performedByUsername;
    private SdmActionType actionType;
    private Instant handoverDate;
    private String comments;
    private Boolean isConfirmed;
    private Instant confirmedAt;

    public SdmHandOverProtocolRequest() {
        // Default constructor
    }

    // Getters & Setters

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

	public void setActionType(SdmActionType actionType) {
	    this.actionType = actionType;

	}

	@Override
	public SdmActionType getSdmActionType() {
		return actionType;
	}


}
