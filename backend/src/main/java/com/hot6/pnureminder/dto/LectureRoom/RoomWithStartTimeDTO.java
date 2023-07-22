package com.hot6.pnureminder.dto.LectureRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWithStartTimeDTO {

    private String roomNum;
    private Time startTime;
}
