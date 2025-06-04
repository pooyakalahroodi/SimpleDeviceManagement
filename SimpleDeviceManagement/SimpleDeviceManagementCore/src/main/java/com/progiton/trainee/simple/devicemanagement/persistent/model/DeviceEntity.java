package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.progiton.trainee.simple.devicemanagement.model.enums.*;
import com.progiton.trainee.simple.devicemanagement.view.Device;

import jakarta.persistence.*;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@EntityListeners(AuditingEntityListener.class)
public class DeviceEntity implements Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;
    
    private String serialNumber;

    private String manufacturer;
    
    private String location;

    private LocalDate purchaseDate;
    
    // Auditing fields =====================
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    // ====================================

    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity assignedTo;
    
    // Optional: if DeviceView includes this
    @Override
    public String getAssignedToName() {
        return assignedTo != null ? assignedTo.getName() : null;
    }
}
