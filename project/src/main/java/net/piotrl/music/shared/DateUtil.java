package net.piotrl.music.shared;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class DateUtil {

    public Date toDate(String stringDateTime) {
        LocalDateTime dateTime = LocalDateTime.parse(stringDateTime);
        return toDate(dateTime);
    }

    public Date toDate(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
