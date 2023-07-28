package com.hot6.pnureminder.dto.LectureRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSoonDto {
    private String roomNum;
    private Time startTime;
    private Time endTime;
}