package com.progiton.trainee.simple.devicemanagement.persistent.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.progiton.trainee.simple.devicemanagement.model.Department;

//TODO (LR) du hast selbst die Kommentar unten, was macht dann diese Annotation hier?
@EnableJpaAuditing  // Add this to your main application class or config
@Entity
@EntityListeners(AuditingEntityListener.class)
public class DepartmentEntity extends SdmBaseEntity<Long> implements Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "departmentEntity")
    private List<UserEntity> userEntity;
    
    // Auditing fields =====================
    // TODO (LR) in DB bitte Instant oder besser java.sql.Timestamp
    // Die sind jetzt in SdmBaseEntity und können hier und in alle andere Entity-Klassen
    // gelöscht werden
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
    // ====================================


    // No-args constructor
    public DepartmentEntity() {
        super();
    }

    // All-args constructor
    public DepartmentEntity(final Long id, final String name, final List<UserEntity> userEntity, final Instant createdAt, final Instant updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.userEntity = userEntity;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(List<UserEntity> userEntity) {
        this.userEntity = userEntity;
    }

}
