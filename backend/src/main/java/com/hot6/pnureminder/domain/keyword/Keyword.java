package com.hot6.pnureminder.domain.keyword;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hot6.pnureminder.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "keywords")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Keyword;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private Member member;
}
