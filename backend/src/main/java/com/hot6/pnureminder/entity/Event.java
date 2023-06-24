package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reminderevents")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long eventId;

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String location;

    @Column(nullable = false, columnDefinition ="Asia/Seoul")
    private ZonedDateTime startTime;

    @Column(nullable = false, columnDefinition ="Asia/Seoul")
    private ZonedDateTime endTime;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private int alarmTime; // 알람이 얼마나 먼저 울릴지 설정하는 시간입니다. 이 값은 분 단위로 저장될 수 있습니다.

}