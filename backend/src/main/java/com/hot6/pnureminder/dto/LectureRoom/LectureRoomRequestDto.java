package com.hot6.pnureminder.dto.LectureRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureRoomRequestDto {

    private String buildingName;
    private LocalTime currentTime;
    private Integer setTime;



}

