package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.LectureRoom.*;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.repository.BuildingRepository;
import com.hot6.pnureminder.service.BuildingService;
import com.hot6.pnureminder.service.LectureRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/lecture-rooms")
public class LectureRoomController {

    private final LectureRoomService lectureRoomService;
    private final BuildingService buildingService;
    private final BuildingRepository buildingRepository;

    @GetMapping("/available")
    public ResponseEntity<BuildingResponseDto> getAvailableLectureRoomsAndLectures(
            @RequestParam String buildingName,
            @RequestParam Integer setTime) {

        Building building = buildingRepository.findByBuildingName(buildingName)
                .orElseThrow(() -> new RuntimeException("찾는 건물이 없습니다."));


        LectureRoomRequestDto requestDto = LectureRoomRequestDto.builder()
                .buildingName(buildingName)
                .currentTime(LocalTime.of(14, 40))  //임시 시간
                .setTime(setTime)
//                .dayOfWeek(DateTimeUtilsForTest.getCurrentDayOfWeekAsInt())
                .dayOfWeek(0)
                .build();

        List<AvailableNowDto> nowAvailableResponse= lectureRoomService.getNowAvailableLectureRoomsAndLectures(requestDto);
//        List<AvailableSoonDto> soonAvailableResponse = lectureRoomService.getSoonAvailableLectureRoomsAndLectures(requestDto);

        BuildingResponseDto buildingResponseDto = BuildingResponseDto.builder()
                .buildingName(buildingName)
                .availableNow(nowAvailableResponse)
//                .availableSoon(soonAvailableResponse)
                .buildingLat(building.getBuildingLat())
                .buildingLng(building.getBuildingLng())
                .build();

        return ResponseEntity.ok(buildingResponseDto);
    }

    @GetMapping("/available-list")
    public ResponseEntity<List<BuildingResponseDto>> getListOfAvailableLectureRoomsAndLectures(
            @RequestParam("user_latitude") double latitude,
            @RequestParam("user_longitude") double longitude,
            @RequestParam Integer setTime
    ) {
        List<Building> buildings = buildingService.findNearestBuildings(latitude, longitude);
        List<BuildingResponseDto> responses = new ArrayList<>();

        for (Building building : buildings) {
            String buildingName = building.getBuildingName();

            LectureRoomRequestDto requestDto = LectureRoomRequestDto.builder()
                    .buildingName(buildingName)
                    .currentTime(LocalTime.of(14, 40))  //임시 시간
                    .setTime(setTime)
                    .dayOfWeek(0)
                    .build();

            List<AvailableNowDto> nowAvailableResponse= lectureRoomService.getNowAvailableLectureRoomsAndLectures(requestDto);

            if (!nowAvailableResponse.isEmpty()) {
                BuildingResponseDto buildingResponseDto = BuildingResponseDto.builder()
                        .buildingName(buildingName)
                        .availableNow(nowAvailableResponse)
                        .buildingLat(building.getBuildingLat())
                        .buildingLng(building.getBuildingLng())
                        .build();
                responses.add(buildingResponseDto);
            }
        }

        return ResponseEntity.ok(responses);
    }


}

/**@GetMapping("/available-dsl")
public ResponseEntity<BuildingResponseDto> getAvailableRooms(
        @RequestParam String buildingName,
        @RequestParam Integer setTime) {

    LectureRoomRequestDto requestDto = LectureRoomRequestDto.builder()
            .buildingName(buildingName)
            .currentTime(LocalTime.of(14, 40))  //임시 시간
            .setTime(setTime)
            .dayOfWeek(DateTimeUtilsForTest.getCurrentDayOfWeekAsInt())
            .dayOfWeek(0)
            .build();

    BuildingResponseDto buildingResponseDto = lectureRoomService.getAvailableRooms(requestDto);
    return ResponseEntity.ok(buildingResponseDto);
}**/
