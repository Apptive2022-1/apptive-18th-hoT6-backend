package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {

    private Integer id;
    private Time startTime;
    private Time runTime;
    private Integer dayOfWeek;
    private Integer lectureRoomId;

    public static LectureDto toDto(Lecture lecture) {
        return new LectureDto(
                lecture.getId(),
                lecture.getStartTime(),
                lecture.getRunTime(),
                lecture.getDayOfWeek(),
                lecture.getLectureRoomId()
        );
    }
}
