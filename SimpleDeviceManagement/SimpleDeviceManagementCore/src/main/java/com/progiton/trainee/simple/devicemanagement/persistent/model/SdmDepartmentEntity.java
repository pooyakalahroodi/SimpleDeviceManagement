package com.progiton.trainee.simple.devicemanagement.persistent.model;

import jakarta.persistence.*;
import java.util.List;
import com.progiton.trainee.simple.devicemanagement.model.SdmDepartment;

@Entity
@Table(name = "departments")
public class SdmDepartmentEntity extends SdmBaseEntity<Long> implements SdmDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "sdmDepartmentEntity")
    private List<SdmUserEntity> sdmUserEntity;
    
    // No-args constructor
    public SdmDepartmentEntity() {
    }

    // All-args constructor
    public SdmDepartmentEntity(final Long id, final String name, final List<SdmUserEntity> sdmUserEntity) {
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
