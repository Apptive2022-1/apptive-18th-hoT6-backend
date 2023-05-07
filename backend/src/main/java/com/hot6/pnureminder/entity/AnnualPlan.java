package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "annualplan")
public class AnnualPlan {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "startDate")
    private String startDate;
    @Column(name = "endDate")
    private String endDate;

    @Column(name = "context")
    private String context;

    @Column(name = "state")
    private Integer state;


}
