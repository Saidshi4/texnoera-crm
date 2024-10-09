package com.example.texnoeracrm.dao.entity;

import com.example.texnoeracrm.enums.AttendanceEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "attendances")
@Builder
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity userEntity;
    @ManyToOne
    private GroupEntity groupEntity;
    @Enumerated(EnumType.STRING)
    private AttendanceEnum status;
    private LocalDate expectedAttendanceDate;
    private LocalDateTime createdAt;
}