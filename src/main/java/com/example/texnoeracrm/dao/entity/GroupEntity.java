package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "groupEntity")
    private List<UserGroupEntity> userGroupEntities;

    @OneToMany(mappedBy = "groupEntity")
    private List<AttendanceEntity> attendanceEntities;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "groupEntity")
    private List<TaskEntity> taskEntities;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "group_days", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysOfWeek;

    @ElementCollection
    @CollectionTable(name = "group_lesson_start_times", joinColumns = @JoinColumn(name = "group_id"))
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "lesson_start_time")
    @Enumerated(EnumType.STRING)
    private Map<DayOfWeek, LocalTime> lessonStartTimes;

    @ElementCollection
    @CollectionTable(name = "group_lesson_end_times", joinColumns = @JoinColumn(name = "group_id"))
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "lesson_end_time")
    @Enumerated(EnumType.STRING)
    private Map<DayOfWeek, LocalTime> lessonEndTimes;
}
