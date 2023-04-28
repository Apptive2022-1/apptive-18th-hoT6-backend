package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LectureRoomDto {
    private Integer id;
    private String roomNum;
    private List<Lecture> lectures;
    private Lecture earliestStartLecture;


    public static LectureRoomDto toDto(LectureRoom lectureRoom){

        return new LectureRoomDto(
                lectureRoom.getId(),
                lectureRoom.getRoomNum(),
                lectureRoom.getLectures(),
                null
        );
    }

    public static LectureRoomDto toDto(LectureRoom lectureRoom, Lecture earliestStartLecture) {
        return new LectureRoomDto(
                lectureRoom.getId(),
                lectureRoom.getRoomNum(),
                null,
                earliestStartLecture
        );
    }

    // 기본 생성자는 private으로 설정하여 외부에서 호출되지 않도록 한다.
    private LectureRoomDto(Integer id, String roomNum, List<Lecture> lectures, Lecture earliestStartLecture) {
        this.id = id;
        this.roomNum = roomNum;
        this.lectures = lectures;
        this.earliestStartLecture = earliestStartLecture;
    }


}
