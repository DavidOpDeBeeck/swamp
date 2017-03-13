package de.daxu.swamp.test.rule;

import de.daxu.swamp.common.time.Dates;
import org.junit.rules.ExternalResource;

import java.time.LocalDateTime;

public class DateRule extends ExternalResource {

    public static DateRule now() {
        return new DateRule(Dates.now());
    }

    public static DateRule at(LocalDateTime date) {
        return new DateRule(date);
    }

    private LocalDateTime date;

    private DateRule(LocalDateTime date) {
        this.date = date;
    }

    @Override
    protected void before() throws Exception {
        Dates.useFixedClockAt(date);
    }
}