package com.progiton.trainee.simple.devicemanagement.persistent.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
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
    }



