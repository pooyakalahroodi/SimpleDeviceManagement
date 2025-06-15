package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.Instant;

public class SdmBaseTO {

    private Instant createdAt;
    private Instant updatedAt;

    public SdmBaseTO() {
    }

    public SdmBaseTO(Instant createdAt, Instant updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
