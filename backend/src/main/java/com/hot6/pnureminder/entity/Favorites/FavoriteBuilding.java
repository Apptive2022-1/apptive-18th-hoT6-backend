package com.hot6.pnureminder.entity.Favorites;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Department;
import com.hot6.pnureminder.entity.Member;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "favorite_building")
public class FavoriteBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Building building;

}
