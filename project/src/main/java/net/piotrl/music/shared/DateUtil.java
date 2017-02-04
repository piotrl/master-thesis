package net.piotrl.music.shared;

import lombok.experimental.UtilityClass;

import java.time.*;
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

    public LocalDate toLocalDate(Date date) {
        return toZonedDateTime(date).toLocalDate();
    }

    public LocalDateTime toLocalDateTime(Date date) {
        return toZonedDateTime(date).toLocalDateTime();
    }


    private ZonedDateTime toZonedDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }
}
