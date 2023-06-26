package com.hot6.pnureminder.util;

import com.hot6.pnureminder.entity.Lecture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    public static ZonedDateTime getCurrentSeoulTime() {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime seoulZonedDateTime = ZonedDateTime.now(seoulZoneId);
        return seoulZonedDateTime;
    }

    public static ZonedDateTime getLectureStartTime(Lecture lecture) {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        return lecture.getStartTime().toLocalTime().atDate(LocalDate.now()).atZone(seoulZoneId);
    }

    public static ZonedDateTime getLectureEndTime(Lecture lecture) {
        ZonedDateTime lectureStartTime = getLectureStartTime(lecture);
        int hours = lecture.getRunTime().toLocalTime().getHour();
        int minutes = lecture.getRunTime().toLocalTime().getMinute();
        return lectureStartTime.plus(hours, ChronoUnit.HOURS).plus(minutes, ChronoUnit.MINUTES);
    }
}
