package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "building_location")
public class Building {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column(name = "building_num")
    private Integer buildingNum;
    @Column(name = "building_name")
    private String buildingName;
    @Column(name = "building_lat")
    private String buildingLat;
    @Column(name = "building_lng")
    private String buildingLng;



}
