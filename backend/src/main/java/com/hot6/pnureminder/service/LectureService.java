package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.LectureDto;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.repository.LectureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {

    private  final LectureRepository lectureRepository;

    public List<LectureDto> findAllByLectureRoomId(Integer lectureRoomId) {
        List<Lecture> lectureRooms = lectureRepository.findAllByLectureRoomId(lectureRoomId);

        return lectureRooms.stream()
                .map(LectureDto::toDto)
                .collect(Collectors.toList());
    }

}

