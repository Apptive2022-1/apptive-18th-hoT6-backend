package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.LectureRoom.LectureRoomRequestDto;
import com.hot6.pnureminder.dto.LectureRoom.LectureRoomWithLecturesResponse;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import com.hot6.pnureminder.service.LectureRoomService;
import com.hot6.pnureminder.util.DateTimeUtilsForTest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/lecture-rooms")
public class LectureRoomController {

    private final LectureRoomService lectureRoomService;
    @GetMapping("/available")
    public ResponseEntity<List<LectureRoom>> getAvailableLectureRooms(
            @RequestParam String buildingName,
            @RequestParam Integer dayOfWeek,
            @RequestParam Integer setTime) {

        // DTO 객체 생성 및 데이터 설정
        LectureRoomRequestDto requestDto = LectureRoomRequestDto.builder()
                .buildingName(buildingName)
                .dayOfWeek(dayOfWeek)
                .currentTime(LocalTime.of(14,40))
                .setTime(setTime)
                .build();

        List<LectureRoom> lectureRooms = lectureRoomService.getAvailableLectureRooms(requestDto);
        return ResponseEntity.ok(lectureRooms);
    }

    @GetMapping("/available-with-lectures")
    public ResponseEntity<List<LectureRoomWithLecturesResponse>> getAvailableLectureRoomsAndLectures(
            @RequestParam String buildingName,
            @RequestParam Integer dayOfWeek,
            @RequestParam Integer setTime) {

        LectureRoomRequestDto requestDto = LectureRoomRequestDto.builder()
                .buildingName(buildingName)
                .dayOfWeek(dayOfWeek)
                .currentTime(LocalTime.of(14, 40))  //임시 시간
                .setTime(setTime)
                .build();

        List<Object[]> results = lectureRoomService.getAvailableLectureRoomsAndLectures(requestDto);

        List<LectureRoomWithLecturesResponse> response = results.stream()
                .map(result -> new LectureRoomWithLecturesResponse((LectureRoom) result[0], (Lecture) result[1]))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
