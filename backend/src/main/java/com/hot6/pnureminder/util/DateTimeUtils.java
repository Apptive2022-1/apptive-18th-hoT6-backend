package com.hot6.pnureminder.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    public static ZonedDateTime getCurrentSeoulTime() {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime seoulZonedDateTime = localDateTime.atZone(seoulZoneId);
        return seoulZonedDateTime;
    }
}
