package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureInfoDto {
    private LectureDto lectureDto;
    private boolean isMarginTimeLecture;
    private boolean isInProgressLecture;
    private ZonedDateTime inProgressLectureEndTime;

    public LectureInfoDto(Lecture lecture, boolean isMarginTimeLecture, boolean isInProgressLecture, ZonedDateTime inProgressLectureEndTime) {
        this.lectureDto = LectureDto.toDto(lecture);
        this.isMarginTimeLecture = isMarginTimeLecture;
        this.isInProgressLecture = isInProgressLecture;
        this.inProgressLectureEndTime = inProgressLectureEndTime;
    }
}
