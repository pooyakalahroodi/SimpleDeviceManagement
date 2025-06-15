package com.progiton.trainee.simple.devicemanagement.model.to;

import com.progiton.trainee.simple.devicemanagement.model.SdmHandOverProtocol;

import java.time.Instant;

public class SdmHandOverProtocolTo extends SdmBaseTO implements SdmHandOverProtocol {

    private String deviceSerialNumber;
    private String receiverUsername;
    private String performedByUsername;
    private Instant handoverDate;
    private String comments;
    private Boolean isConfirmed;
    private Instant confirmedAt;

    // Constructors
    public SdmHandOverProtocolTo() {
    	super();	
    	
    }
    
    public SdmHandOverProtocolTo(String deviceSerialNumber, String receiverUsername, String performedByUsername,
                              Instant handoverDate, String comments, Boolean isConfirmed, Instant confirmedAt, final Instant createdAt, final Instant updatedAt) {
        super(createdAt, updatedAt);
        this.deviceSerialNumber = deviceSerialNumber;
        this.receiverUsername = receiverUsername;
        this.performedByUsername = performedByUsername;
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
    public Boolean getIsConfirmed() {
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
