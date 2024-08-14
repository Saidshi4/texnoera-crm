package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lesson;
    private LocalDateTime created_at;

    @ManyToMany(mappedBy = "groupEntities", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<UserEntity> userEntities;

    @OneToMany(mappedBy = "groupEntity")
    private List<AttendanceEntity> attendanceEntities;
}
