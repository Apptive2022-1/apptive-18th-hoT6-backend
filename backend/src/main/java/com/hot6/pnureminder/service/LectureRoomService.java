package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.LectureDto;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import com.hot6.pnureminder.repository.LectureRepository;
import com.hot6.pnureminder.repository.LectureRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureRoomService {

    @Autowired
    private LectureRoomRepository lectureRoomRepository;

    public List<LectureRoomDto> findAllByBuildingNum(Integer buildingNum) {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllByBuildingNum(buildingNum);

        return lectureRooms.stream()
                .map(LectureRoomDto::toDto)
                .collect(Collectors.toList());
    }

}
