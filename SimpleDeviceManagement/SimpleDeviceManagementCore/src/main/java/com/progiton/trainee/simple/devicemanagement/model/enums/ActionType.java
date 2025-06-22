package com.progiton.trainee.simple.devicemanagement.model.enums;

public enum ActionType {
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

    ActionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}