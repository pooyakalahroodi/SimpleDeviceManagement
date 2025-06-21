package com.progiton.trainee.simple.devicemanagement.persistent.model;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmPermission;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class SdmRoleEntity extends SdmBaseEntity<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name; // e.g., "ADMIN", "IT_STAFF", "HR", "USER"
    
    private String description;
    
    @ManyToMany(mappedBy = "sdmRoleEntities")
    private Set<SdmUserEntity> sdmUserEntities;
    
    @ElementCollection(targetClass = SdmPermission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_permissions")
    private Set<SdmPermission> sdmPermissions;

    // No-args constructor
    public SdmRoleEntity() {
    }

    // All-args constructor
    public SdmRoleEntity(Long id, String name, String description, 
                        Set<SdmUserEntity> sdmUserEntities, 
                        Set<SdmPermission> sdmPermissions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sdmUserEntities = sdmUserEntities;
        this.sdmPermissions = sdmPermissions;
    }

    // Getters and setters
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SdmUserEntity> getSdmUserEntities() {
        return sdmUserEntities;
    }

    public void setSdmUserEntities(Set<SdmUserEntity> sdmUserEntities) {
        this.sdmUserEntities = sdmUserEntities;
    }

    public Set<SdmPermission> getSdmPermissions() {
        return sdmPermissions;
    }

    public void setSdmPermissions(Set<SdmPermission> sdmPermissions) {
        this.sdmPermissions = sdmPermissions;
    }
}