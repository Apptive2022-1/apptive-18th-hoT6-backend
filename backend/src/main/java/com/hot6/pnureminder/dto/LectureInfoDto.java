package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureInfoDto {
    private LectureDto lectureDto;
    private boolean isMarginTimeLecture;

    public LectureInfoDto(Lecture lecture, boolean isMarginTimeLecture) {
        this.lectureDto = LectureDto.toDto(lecture);
        this.isMarginTimeLecture = isMarginTimeLecture;
    }
}
