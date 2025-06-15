package com.progiton.trainee.simple.devicemanagement.persistent.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.progiton.trainee.simple.devicemanagement.model.SdmDepartment;

//TODO (LR) du hast selbst die Kommentar unten, was macht dann diese Annotation hier?
@EnableJpaAuditing  // Add this to your main application class or config
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SdmDepartmentEntity extends SdmBaseEntity<Long> implements SdmDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "sdmDepartmentEntity")
    private List<SdmUserEntity> sdmUserEntity;
    
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
    public SdmDepartmentEntity() {
        super();
    }

    // All-args constructor
    public SdmDepartmentEntity(final Long id, final String name, final List<SdmUserEntity> sdmUserEntity, final Instant createdAt, final Instant updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.sdmUserEntity = sdmUserEntity;
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

    public List<SdmUserEntity> getUserEntity() {
        return sdmUserEntity;
    }

    public void setUserEntity(List<SdmUserEntity> sdmUserEntity) {
        this.sdmUserEntity = sdmUserEntity;
    }

}
