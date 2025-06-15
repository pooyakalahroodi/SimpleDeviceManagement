package com.progiton.trainee.simple.devicemanagement.persistent.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.progiton.trainee.simple.devicemanagement.model.SdmUser;


@EnableJpaAuditing  // Add this to your main application class or config
@Entity
@Table(name = "users")
public class SdmUserEntity extends SdmBaseEntity<Long> implements SdmUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    private boolean enabled;

    // TODO remove column password
    @Column(nullable = false)
    private String password; // Should be encrypted
    
    private String name;

    @OneToMany(mappedBy = "assignedTo")
    private List<SdmDeviceEntity> sdmDeviceEntities;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private SdmDepartmentEntity sdmDepartmentEntity;
    
    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
        )
    
    
    private Set<SdmRoleEntity> sdmRoleEntities;

 // Constructors
    public SdmUserEntity() {}

    public SdmUserEntity(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getDepartment() {
        return sdmDepartmentEntity != null ? sdmDepartmentEntity.getName() : null;
    }

    public SdmDepartmentEntity getDepartmentEntity() {
        return sdmDepartmentEntity;
    }

    public void setDepartmentEntity(SdmDepartmentEntity sdmDepartmentEntity) {
        this.sdmDepartmentEntity = sdmDepartmentEntity;
    }

    @Override
    public List<String> getDevices() {
        if (sdmDeviceEntities == null || sdmDeviceEntities.isEmpty()) {
            return List.of();
        }
        // Return device info in format: "DeviceName (SerialNumber)"
        return sdmDeviceEntities.stream()
                .map(device -> device.getName() + " (" + device.getSerialNumber() + ")")
                .collect(Collectors.toList());
    }

    public List<SdmDeviceEntity> getDeviceEntities() {
        return sdmDeviceEntities;
    }

    public void setDeviceEntities(List<SdmDeviceEntity> sdmDeviceEntities) {
        this.sdmDeviceEntities = sdmDeviceEntities;
    }

    public Set<SdmRoleEntity> getRoleEntities() {
        return sdmRoleEntities;
    }

    public void setRoleEntities(Set<SdmRoleEntity> sdmRoleEntities) {
        this.sdmRoleEntities = sdmRoleEntities;
    }

    }



