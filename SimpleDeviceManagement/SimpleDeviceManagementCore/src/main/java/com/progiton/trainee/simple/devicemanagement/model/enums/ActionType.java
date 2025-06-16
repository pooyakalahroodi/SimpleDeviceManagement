package com.progiton.trainee.simple.devicemanagement.model.enums;

public enum ActionType {
    HANDOVER("Übergabe"),
    RETURN("Rückgabe"),
    TRANSFER("Transfer");

    private final String label;

    ActionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}