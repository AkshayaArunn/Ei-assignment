package org.example.smartoffice.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class TimeUtils {
    public static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private TimeUtils() {}

    public static LocalDateTime parseTodayOrTomorrow(String hhmm) throws DateTimeParseException {
        LocalTime lt = LocalTime.parse(hhmm, TIME_FMT);
        LocalDate today = LocalDate.now();
        LocalDateTime dt = LocalDateTime.of(today, lt);
        if (dt.isBefore(LocalDateTime.now())) {
            dt = dt.plusDays(1);
        }
        return dt;
    }

    public static boolean intervalsOverlap(LocalDateTime s1, LocalDateTime e1, LocalDateTime s2, LocalDateTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }
}
