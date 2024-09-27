package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private String idCardNo;
    private String personalNo;
    private LocalDate birthdate;
    private String phoneNumber;
    private String email;
    private Long diplomaNo;
    private Double averageScore;
    private String username;
    private String password;
    private Boolean isActive = true;
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.REMOVE, fetch= FetchType.EAGER,mappedBy = "user")
    private List<UserRoleEntity> userRoles;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupEntity> groupEntities;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<TaskEntity> taskEntities;

    @OneToMany(mappedBy = "userEntity")
    private List<AttendanceEntity> attendanceEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<DeviceTokenEntity> deviceTokenEntities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRoles == null) {
            return Collections.emptyList();
        }
        return this.userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
