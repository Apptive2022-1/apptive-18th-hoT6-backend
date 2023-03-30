package com.hot6.pnureminder.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RefreshToken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;


    @Column(name = "refresh_key")
    private String key;

    @Column(name = "refresh_value")
    private String value;


    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
