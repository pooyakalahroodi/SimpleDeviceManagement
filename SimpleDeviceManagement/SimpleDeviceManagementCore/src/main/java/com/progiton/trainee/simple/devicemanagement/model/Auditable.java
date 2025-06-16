package com.progiton.trainee.simple.devicemanagement.model;

import java.time.Instant;

public interface Auditable {
	
    Instant getCreatedAt();
    Instant getUpdatedAt();

}
