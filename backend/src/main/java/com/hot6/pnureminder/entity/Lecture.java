package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Entity
@Data
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "run_time")
    private Time runTime;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "lecture_room_id")
    private Integer lectureRoomId;

    @ManyToOne
    @JoinColumn(name = "lecture_room_id", insertable = false, updatable = false)
    private LectureRoom lectureRoom;

}
