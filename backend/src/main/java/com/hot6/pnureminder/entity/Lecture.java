package com.hot6.pnureminder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;

@Entity
@Data
@Table
public class Lecture {
    @Id
    @Column
    private Integer id;

    @Column(name = "building_num")
    private Integer buildingNum;

    @Column(name = "room_num")
    private String roomNum;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "run_time")
    private Time runtime;
}
