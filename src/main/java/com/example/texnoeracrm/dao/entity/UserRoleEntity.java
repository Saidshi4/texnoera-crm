package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users_roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserEntity user;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="role_id",referencedColumnName = "id")
    private RoleEntity role;
}