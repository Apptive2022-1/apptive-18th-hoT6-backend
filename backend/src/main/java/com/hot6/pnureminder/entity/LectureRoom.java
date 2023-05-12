package com.hot6.pnureminder.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "lecture_room")
@ToString(exclude = {"lectures","building"})
public class LectureRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "room_num")
    private String roomNum;

    @Column(name = "building_num")
    private Integer buildingNum;

    @OneToMany(mappedBy = "lectureRoom")
    @JsonManagedReference
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn(name = "building_num", insertable = false, updatable = false)
    private Building building;
}
