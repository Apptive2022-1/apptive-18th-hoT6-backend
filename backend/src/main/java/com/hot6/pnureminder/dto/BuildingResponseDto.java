package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.LectureRoom;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponseDto {

    private Integer buildingNum;
    private String buildingName;
    private Double buildingLat;
    private Double buildingLng;

    private List<LectureRoomDto> availableLectureRooms;

    public static BuildingResponseDto toDto(Building building, List<LectureRoomDto> availableLectureRooms){

        return new BuildingResponseDto(
            building.getBuildingNum(),
            building.getBuildingName(),
            building.getBuildingLat(),
            building.getBuildingLng(),
                availableLectureRooms
        );

    }

}
