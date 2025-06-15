package com.progiton.trainee.simple.devicemanagement.persistent.model;

import com.progiton.trainee.simple.devicemanagement.model.enums.SdmPermission;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SdmRoleEntity extends SdmBaseEntity<Long>{
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
} 