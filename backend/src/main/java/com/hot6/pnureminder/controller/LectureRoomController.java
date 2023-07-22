package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.LectureRoom.LectureRoomRequestDto;
import com.hot6.pnureminder.dto.LectureRoom.LectureRoomWithLecturesResponse;
import com.hot6.pnureminder.dto.LectureRoom.RoomWithStartTimeDTO;
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
    public ResponseEntity<List<RoomWithStartTimeDTO>> getAvailableLectureRoomsAndLectures(
            @RequestParam String buildingName,
            @RequestParam Integer setTime) {

        LectureRoomRequestDto requestDto = LectureRoomRequestDto.builder()
                .buildingName(buildingName)
                .currentTime(LocalTime.of(14, 40))  //임시 시간
                .setTime(setTime)
                .build();

        List<RoomWithStartTimeDTO> response = lectureRoomService.getAvailableLectureRoomsAndLectures(requestDto);

        return ResponseEntity.ok(response);
    }

}
