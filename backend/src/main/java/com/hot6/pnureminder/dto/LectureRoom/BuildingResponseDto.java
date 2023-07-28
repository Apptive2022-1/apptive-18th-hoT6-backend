package com.hot6.pnureminder.dto.LectureRoom;

import com.hot6.pnureminder.entity.Building;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponseDto {
    private String buildingName;
    private List<AvailableNowDto> availableNow;
//    private List<AvailableSoonDto> availableSoon;
    private double buildingLat;
    private double buildingLng;
}
