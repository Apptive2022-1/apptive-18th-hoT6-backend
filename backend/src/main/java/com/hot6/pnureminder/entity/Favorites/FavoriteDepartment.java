package com.hot6.pnureminder.entity.Favorites;

import com.hot6.pnureminder.entity.Department;
import com.hot6.pnureminder.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "favorite_department")
public class FavoriteDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private boolean isAlert = true; // 초기값을 true로 설정

    @Column
    private String keyword;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Department department;

}
