package com.progiton.trainee.simple.devicemanagement.model.to;

import com.progiton.trainee.simple.devicemanagement.model.HandOverProtocol;

import java.time.LocalDateTime;

public class HandOverProtocolTo implements HandOverProtocol {

    private String deviceSerialNumber;
    private String receiverUsername;
    private String performedByUsername;
    private LocalDateTime handoverDate;
    private String comments;
    private Boolean isConfirmed;
    private LocalDateTime confirmedAt;

    public HandOverProtocolTo() {
        // Default constructor
    }

    public HandOverProtocolTo(String deviceSerialNumber, String receiverUsername, String performedByUsername,
                              LocalDateTime handoverDate, String comments, Boolean isConfirmed, LocalDateTime confirmedAt) {
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
