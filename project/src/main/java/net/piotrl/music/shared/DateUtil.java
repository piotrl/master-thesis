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
        return toDate(toLocalDateTime(stringDateTime));
    }

    public Date toDate(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public Date toDate(LocalDate dateTime) {
        return toDate(dateTime.atStartOfDay());
    }

    public LocalDate toLocalDate(Date date) {
        return toZonedDateTime(date).toLocalDate();
    }

    public LocalDate toLocalDate(String dateString) {
        return LocalDate.parse(dateString);
    }

    public LocalDateTime toLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString);
    }

    public LocalDateTime toLocalDateTime(Date date) {
        return toZonedDateTime(date).toLocalDateTime();
    }

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(IsoDateTimeFormatter);
    }

    public String formatDate(LocalDate date) {
        return date.format(IsoDateFormatter);
    }

    public String formatDate(Date date) {
        return formatDate(toLocalDate(date));
    }

    private ZonedDateTime toZonedDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }
}
