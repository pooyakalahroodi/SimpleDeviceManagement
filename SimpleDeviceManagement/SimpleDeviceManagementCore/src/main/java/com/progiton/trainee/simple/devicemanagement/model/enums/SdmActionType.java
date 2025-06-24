package com.progiton.trainee.simple.devicemanagement.model.enums;

public enum SdmActionType {
    /**
     * Übergabe von Gerät an User
     */
    HANDOVER("Übergabe"),

    /**
     * Rückgabe von Gerät durch User
     */
    RETURN("Rückgabe"),

    /**
     *
     */
    TRANSFER("Transfer");

    private final String label;

    SdmActionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}