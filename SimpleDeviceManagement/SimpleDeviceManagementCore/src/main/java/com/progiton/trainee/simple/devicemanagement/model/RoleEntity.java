package com.progiton.trainee.simple.devicemanagement.model;

import com.progiton.trainee.simple.devicemanagement.model.enums.Permission;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name; // e.g., "ADMIN", "IT_STAFF", "HR", "USER"
    
    private String description;
    
    @ManyToMany(mappedBy = "roleEntities")
    private Set<UserEntity> userEntities;
    
    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_permissions")
    private Set<Permission> permissions;
}