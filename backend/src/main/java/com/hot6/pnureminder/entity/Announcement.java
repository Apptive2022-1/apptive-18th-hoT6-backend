package com.hot6.pnureminder.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="announcement")
public class Announcement {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "urls")
    private String urls;

    @Column(name = "date")
    private String date;
}
