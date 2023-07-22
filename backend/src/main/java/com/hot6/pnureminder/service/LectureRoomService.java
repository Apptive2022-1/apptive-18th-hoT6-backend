package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.LectureRoom.LectureRoomRequestDto;
import com.hot6.pnureminder.dto.LectureRoom.LectureRoomWithLecturesResponse;
import com.hot6.pnureminder.dto.LectureRoom.RoomWithStartTimeDTO;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import com.hot6.pnureminder.repository.LectureRoomRepository;
import com.hot6.pnureminder.util.DateTimeUtilsForTest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Time;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureRoomService {

    private final LectureRoomRepository lectureRoomRepository;

    public List<LectureRoom> findAllByBuildingNum(Integer buildingNum) {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllByBuildingNum(buildingNum);

        return lectureRooms;
    }


//    public List<LectureRoomWithLecturesResponse> getAvailableLectureRoomsAndLectures(LectureRoomRequestDto requestDto) {
//        LocalTime currentTime = requestDto.getCurrentTime();
//        LocalTime endOfOngoingLecture = currentTime.plusMinutes(requestDto.getSetTime());
//
//        List<Object[]> rawResults = lectureRoomRepository.findAvailableLectureRoomsAndLectures(
//                requestDto.getBuildingName(),
//                requestDto.getDayOfWeek(),
//                endOfOngoingLecture,
//                currentTime
//        );
//
//        return rawResults.stream()
//                .map(result -> {
//                    LectureRoom lectureRoom = (LectureRoom) result[0];
//                    Lecture lecture = (result[1] != null) ? (Lecture) result[1] : null;
//                    return new LectureRoomWithLecturesResponse(lectureRoom, lecture);
//                })
//                .collect(Collectors.toList());
//    }

    public List<RoomWithStartTimeDTO> getAvailableLectureRoomsAndLectures(LectureRoomRequestDto requestDto) {
        LocalTime currentTime = requestDto.getCurrentTime();
        LocalTime endOfOngoingLecture = currentTime.plusMinutes(requestDto.getSetTime());
        // 실제 작동 코드
        // Integer dayOfWeek = DateTimeUtilsForTest.getCurrentDayOfWeekAsInt();
        Integer dayOfWeek = 0;

        List<Object[]> rawData = lectureRoomRepository.findAvailableLectureRoomsAndLectures(
                requestDto.getBuildingName(),
                dayOfWeek,
                endOfOngoingLecture,
                currentTime
        );

        Map<String, RoomWithStartTimeDTO> roomToStartTimeMap = new HashMap<>();

        for(Object[] row : rawData) {
            LectureRoom lectureRoom = (LectureRoom) row[0];
            Lecture lecture = (Lecture) row[1];
            String roomNum = lectureRoom.getRoomNum();

            if(!roomToStartTimeMap.containsKey(roomNum) ||
                    (lecture != null &&
                            (roomToStartTimeMap.get(roomNum).getStartTime() == null ||
                                    roomToStartTimeMap.get(roomNum).getStartTime().after(lecture.getStartTime())))) {
                roomToStartTimeMap.put(roomNum, new RoomWithStartTimeDTO(roomNum, lecture == null ? null : lecture.getStartTime()));
            }
        }

        return new ArrayList<>(roomToStartTimeMap.values());
    }




//    public RoomWithStartTimeDTO convertToRoomWithStartTimeDTO(LectureRoomWithLecturesResponse response) {
//        Time startTime = null;
//        if (!response.getLectures().isEmpty()) {
//           startTime = response.getLectures().get(0).getStartTime();
//        }
//        return new RoomWithStartTimeDTO(response.getRoomNum(), (startTime != null) ? startTime.toString() : null);
//    }






}
