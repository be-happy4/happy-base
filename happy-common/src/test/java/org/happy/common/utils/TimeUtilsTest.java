package org.happy.common.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUtilsTest {

    void assertFormatter(DateTimeFormatter formatter, LocalDateTime expected, String toParse) {
        var actual = formatter.parse(toParse, LocalDateTime::from);
        assertEquals(expected, actual);
        assertEquals(formatter.format(expected), formatter.format(actual));
    }

    void assertFormatter(DateTimeFormatter formatter, LocalDateTime expected) {
        assertFormatter(formatter, expected, formatter.format(expected));
    }

    @Test
    void testFmtDefault() {
        assertFormatter(TimeUtils.FMT_DEFAULT, LocalDate.of(2024, 1, 1).atStartOfDay(),
                "2024-01-01");
        assertFormatter(TimeUtils.FMT_DEFAULT, LocalDateTime.of(2024, 1, 2, 3, 4, 0),
                "2024-01-02T03:04");
        assertFormatter(TimeUtils.FMT_DEFAULT, LocalDateTime.of(2024, 1, 2, 3, 4, 5),
                "2024-01-02T03:04:05.000");
        assertFormatter(TimeUtils.FMT_DEFAULT, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void testFormatLocalDateTime() {
        var time_2013_04_05 = LocalDate.of(2013, 4, 5);
        assertEquals("2013-04-05", TimeUtils.format(time_2013_04_05));
        var time_2013_04_05_07_08_09 = LocalDateTime.of(time_2013_04_05, LocalTime.of(7, 8, 9));
        assertEquals("2013-04-05T07:08:09", TimeUtils.format(time_2013_04_05_07_08_09));
        assertEquals("2013-04-05T07:08:09.987", TimeUtils.format(LocalDateTime.of(time_2013_04_05,
                LocalTime.of(7, 8, 9, 987000000))));
        assertEquals("2013-04-05T07:08:09.987654", TimeUtils.format(LocalDateTime.of(time_2013_04_05,
                LocalTime.of(7, 8, 9, 987654000))));
        assertEquals("2013-04-05T07:08:09.987654321", TimeUtils.format(LocalDateTime.of(time_2013_04_05,
                LocalTime.of(7, 8, 9, 987654321))));
    }

    @Test
    void testFormatInstant() {
        var time_2013_04_05 = LocalDate.of(2013, 4, 5);
        var instant_2013_04_05 = time_2013_04_05.atStartOfDay()
                .toInstant(TimeUtils.getZoneOffsetShanghai());
        assertEquals("2013-04-05T00:00:00", TimeUtils.format(instant_2013_04_05));
        var time_2013_04_05_07_08_09 = LocalDateTime.of(time_2013_04_05, LocalTime.of(7, 8, 9));
        assertEquals("2013-04-05T07:08:09", TimeUtils.format(time_2013_04_05_07_08_09));
        var instant_2013_04_05_07_08_09 = time_2013_04_05_07_08_09
                .toInstant(TimeUtils.getZoneOffsetShanghai());
        assertEquals("2013-04-05T07:08:09", TimeUtils.format(instant_2013_04_05_07_08_09));
        var instantNow = Instant.now();
        var localTimeNow = LocalDateTime.ofInstant(instantNow, TimeUtils.getZoneOffsetShanghai());
        assertEquals(TimeUtils.format(instantNow), TimeUtils.format(localTimeNow));
    }

    @Test
    void testZoneIdShanghai() {
        var instantNow = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        var parsedUTC = DateTimeFormatter.ISO_DATE_TIME.parse(instantNow.toString(), LocalDateTime::from);
        var parsedShangHai = TimeUtils.parse(TimeUtils.format(instantNow));
        var duration = Duration.between(parsedUTC, parsedShangHai);
        assertEquals(8, duration.toHours());
    }
}