package com.hot6.pnureminder.dto.LectureRoom;

import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LectureRoomWithLecturesDto {
    private LectureRoom lectureRoom;
    private List<Lecture> lectures;
}
