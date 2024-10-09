package com.example.texnoeracrm.dao.entity;

import com.example.texnoeracrm.enums.GenderEnum;
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

@Builder
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
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String phoneNumber;
    private String email;
    private Long diplomaNo;
    private Double averageScore;
    private String username;
    private String password;
    private Boolean isActive = true;
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private RoleEntity roleEntity;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupEntity> groupEntities;

    @OneToMany(mappedBy = "userEntity")
    private List<AttendanceEntity> attendanceEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DeviceTokenEntity> deviceTokenEntities;

    @OneToMany(mappedBy = "userEntity")
    private List<UserTaskEntity> userTaskEntities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (this.roleEntity != null ? this.roleEntity.getName() : "UNKNOWN")));
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
