package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.LocalDateTime;

public class SdmBaseTO {
    // TODO (LR) : implementiere bitte dies Analog zu SdmBaseEntity

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SdmBaseTO() {
    }

    public SdmBaseTO(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
