package net.piotrl.music.shared;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class DateUtil {

    private DateTimeFormatter IsoDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter IsoDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(IsoDateTimeFormatter);
    }

    public String formatDate(LocalDate dateTime) {
        return dateTime.format(IsoDateFormatter);
    }

    private ZonedDateTime toZonedDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }
}
