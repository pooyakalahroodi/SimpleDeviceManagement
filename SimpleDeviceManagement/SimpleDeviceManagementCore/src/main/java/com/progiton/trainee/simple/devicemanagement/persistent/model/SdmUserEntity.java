package com.progiton.trainee.simple.devicemanagement.persistent.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;


import com.progiton.trainee.simple.devicemanagement.model.SdmUser;


@Entity
@Table(name = "users")
public class SdmUserEntity extends SdmBaseEntity<Long> implements SdmUser<SdmDeviceEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    private boolean enabled;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<SdmDeviceEntity> sdmDevices;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private SdmDepartmentEntity sdmDepartment;
    
    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
        )
    private Set<SdmRoleEntity> sdmRole;

 // Constructors
    public SdmUserEntity() {}

    public SdmUserEntity(String username, String name, String password) {
        this.username = username;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public SdmDepartmentEntity getDepartment() {
        return sdmDepartment;
    }
    
    public void setDepartment(SdmDepartmentEntity sdmDepartmentEntity) {
        this.sdmDepartment = sdmDepartmentEntity;
    }

    @Override
    public List<SdmDeviceEntity> getDevices() {
        return sdmDevices;
    }

    public void setDevices(final List<SdmDeviceEntity> sdmDevice) {
        this.sdmDevices = sdmDevice;
    }

    public Set<SdmRoleEntity> getRoles() {
        return sdmRole;
    }

    public void setRoles(Set<SdmRoleEntity> sdmRoleEntities) {
        this.sdmRole = sdmRoleEntities;
    }

    }



