package com.hot6.pnureminder.entity;

import com.hot6.pnureminder.entity.Favorites.FavoriteBuilding;
import com.hot6.pnureminder.entity.Favorites.FavoriteDepartment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "building_location")
public class Building {

    @Id
    @Column(name = "building_num")
    private Integer buildingNum;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "building_lat")
    private Double buildingLat;

    @Column(name = "building_lng")
    private Double buildingLng;

    @Transient
    private List<LectureRoom> availableLectureRooms = new ArrayList<>();

    public List<LectureRoom> getAvailableLectureRooms() {
        return availableLectureRooms;
    }

    public void setAvailableLectureRooms(List<LectureRoom> availableLectureRooms) {
        this.availableLectureRooms = availableLectureRooms;
    }

}
