package de.daxu.swamp.common.time;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Dates {

    public static final ZoneId ZONE_ID = ZoneId.systemDefault();
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static Clock clock = Clock.systemDefaultZone();

    public static LocalDateTime now() {
        System.out.println(LocalDateTime.now(getClock()));
        return LocalDateTime.now(getClock());
    }

    public static void useFixedClockAt(LocalDateTime date) {
        clock = Clock.fixed(date.atZone(ZONE_ID).toInstant(), ZONE_ID);
    }

    public static void useSystemDefaultZoneClock() {
        clock = Clock.systemDefaultZone();
    }

    private static Clock getClock() {
        return clock;
    }
}
