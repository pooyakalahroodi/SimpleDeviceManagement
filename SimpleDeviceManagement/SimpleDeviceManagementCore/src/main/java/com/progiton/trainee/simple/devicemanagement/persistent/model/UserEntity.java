package com.progiton.trainee.simple.devicemanagement.persistent.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.progiton.trainee.simple.devicemanagement.model.User;


@EnableJpaAuditing  // Add this to your main application class or config
@Entity
@Table(name = "users")
public class UserEntity implements User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    private boolean enabled;

    @Column(nullable = false)
    private String password; // Should be encrypted
    
    private String name;

    @OneToMany(mappedBy = "assignedTo")
    private List<DeviceEntity> deviceEntities;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departmentEntity;
    
    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
        )
    
    
    private Set<RoleEntity> roleEntities;
    
    // Auditing fields =====================
    @CreatedDate
    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    // ====================================
    
 // Constructors
    public UserEntity() {}

    public UserEntity(String username, String name, String password) {
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
        return departmentEntity != null ? departmentEntity.getName() : null;
    }

    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    @Override
    public List<String> getDevices() {
        if (deviceEntities == null || deviceEntities.isEmpty()) {
            return List.of();
        }
        // Return device info in format: "DeviceName (SerialNumber)"
        return deviceEntities.stream()
                .map(device -> device.getName() + " (" + device.getSerialNumber() + ")")
                .collect(Collectors.toList());
    }

    public List<DeviceEntity> getDeviceEntities() {
        return deviceEntities;
    }

    public void setDeviceEntities(List<DeviceEntity> deviceEntities) {
        this.deviceEntities = deviceEntities;
    }

    public Set<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(Set<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    }



