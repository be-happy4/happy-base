package org.happy.common.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoField.NANO_OF_SECOND;


/**
 * Time utils
 *
 * @author happy
 * @see <a href="https://stackoverflow.com/questions/41427384/how-to-get-default-zoneoffset-in-java-8">
 * How to get default ZoneOffset in Java 8?</a>
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeUtils {
    /**
     * Default date time pattern
     *
     * @see DateTimeFormatter#ofPattern(String)
     */
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * Zone key: Shanghai
     *
     * @see ZoneId#SHORT_IDS
     */
    public static final String ZONE_KEY_SHANGHAI = "Asia/Shanghai";
    public static final ZoneId ZONE_ID_SHANGHAI = ZoneId.of(ZONE_KEY_SHANGHAI);

    public static final DateTimeFormatter FMT_DATE = DateTimeFormatter.ISO_DATE;
    /**
     * Default date time formatter, use{@link #ZONE_ID_SHANGHAI} as {@link ZoneId}
     *
     * @see DateTimeFormatter#ISO_DATE_TIME
     * @see DateTimeFormatter#ISO_LOCAL_TIME
     */
    public static final DateTimeFormatter FMT_DEFAULT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .optionalStart()
            .appendLiteral('T').appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .optionalStart()
            .appendFraction(NANO_OF_SECOND, 0, 9, true)
            .optionalEnd().optionalEnd().optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter()
            .withZone(ZONE_ID_SHANGHAI);

    public static LocalDateTime parse(String str) {
        return parse(str, FMT_DEFAULT);
    }

    public static LocalDateTime parse(String str, DateTimeFormatter fmt) {
        TemporalAccessor ta = fmt.parse(str);
        return LocalDateTime.of(
                secureGet(ta, ChronoField.YEAR),
                secureGet(ta, ChronoField.MONTH_OF_YEAR),
                secureGet(ta, ChronoField.DAY_OF_MONTH),
                secureGet(ta, ChronoField.HOUR_OF_DAY),
                secureGet(ta, ChronoField.MINUTE_OF_HOUR),
                secureGet(ta, ChronoField.SECOND_OF_MINUTE),
                secureGet(ta, NANO_OF_SECOND)
        );
    }

    private static int secureGet(TemporalAccessor ta, ChronoField cf) {
        return ta.isSupported(cf) ? ta.get(cf) : (int) cf.range().getMinimum();
    }

    @Nullable
    public static LocalDateTime parseNullable(@Nullable String str) {
        return parseNullable(str, FMT_DEFAULT);
    }

    @Nullable
    public static LocalDateTime parseNullable(@Nullable String str, DateTimeFormatter fmt) {
        if (str == null || (str = str.trim()).isEmpty()) {
            return null;
        }
        try {
            return parse(str, fmt);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Nullable
    public static String format(@Nullable TemporalAccessor time) {
        return format(time, FMT_DEFAULT);
    }

    @Nullable
    public static String format(@Nullable TemporalAccessor time, String format) {
        return format(time, DateTimeFormatter.ofPattern(format));
    }

    @Nullable
    public static String format(@Nullable TemporalAccessor time, DateTimeFormatter formatter) {
        return time == null ? null : formatter.format(time);
    }

    static final Cache<ZoneId, ZoneOffset> CACHE_OFFSET = CacheBuilder.newBuilder()
            .weakValues()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    public static ZoneOffset getZoneOffsetShanghai() {
        return getZoneOffset(ZONE_ID_SHANGHAI);
    }

    public static ZoneOffset getZoneOffset(ZoneId zoneId) {
        try {
            return CACHE_OFFSET.get(zoneId, () -> Instant.now().atZone(zoneId).getOffset());
        } catch (ExecutionException e) {
            return Instant.now().atZone(zoneId).getOffset();
        }
    }
}