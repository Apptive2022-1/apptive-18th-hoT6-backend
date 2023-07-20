package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.LectureRoom.LectureRoomRequestDto;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.LectureRoom;
import com.hot6.pnureminder.repository.LectureRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
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

    public List<LectureRoom> getAvailableLectureRooms(LectureRoomRequestDto requestDto) {
        LocalTime currentTime = requestDto.getCurrentTime();
        LocalTime endOfOngoingLecture = currentTime.plusMinutes(requestDto.getSetTime());
        LocalTime startOfNextLecture = currentTime.plusMinutes(requestDto.getSetTime());

        return lectureRoomRepository.findAvailableLectureRooms(
                requestDto.getBuildingName(),
                requestDto.getDayOfWeek(),
                endOfOngoingLecture,
                startOfNextLecture
        );
    }




}
