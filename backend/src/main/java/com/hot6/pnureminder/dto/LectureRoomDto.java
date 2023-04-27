package com.hot6.pnureminder.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureRoomDto {
    private Integer id;
    private List<Lecture> lectures;

    public static LectureRoomDto toDto(LectureRoom lectureRoom){

        return new LectureRoomDto(
                lectureRoom.getId(),
                lectureRoom.getLectures()
        );
    }
}
