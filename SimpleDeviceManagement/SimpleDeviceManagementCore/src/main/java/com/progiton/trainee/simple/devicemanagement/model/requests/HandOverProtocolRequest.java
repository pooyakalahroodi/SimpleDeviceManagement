package com.progiton.trainee.simple.devicemanagement.model.requests;

import java.time.LocalDateTime;

import com.progiton.trainee.simple.devicemanagement.model.HandOverProtocol;

/**
 * This class represents the input request sent from the client when creating a new handover protocol.
 * It implements HandOverProtocol for compatibility with shared interface, but is only meant for input.
 */
public class HandOverProtocolRequest implements HandOverProtocol {

    private String deviceSerialNumber;
    private String receiverUsername;
    private String performedByUsername;
    private LocalDateTime handoverDate;
    private String comments;
    private Boolean isConfirmed;
    private LocalDateTime confirmedAt;

    public HandOverProtocolRequest() {
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
    public LocalDateTime getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(LocalDateTime handoverDate) {
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
    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }
}
