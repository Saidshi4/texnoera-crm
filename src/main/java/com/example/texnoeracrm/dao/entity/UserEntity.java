package com.example.texnoeracrm.dao.entity;

import com.example.texnoeracrm.enums.GenderEnum;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String username;
    private String password;
    @Builder.Default
    private Boolean isActive = true;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_entity_id")
    private RoleEntity roleEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<UserGroupEntity> userGroupEntities;

    @OneToMany(mappedBy = "userEntity")
    private List<AttendanceEntity> attendanceEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<DeviceTokenEntity> deviceTokenEntities;

    @OneToMany(mappedBy = "userEntity")
    private List<UserTaskEntity> userTaskEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<NotificationEntity> notifications;

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
