package de.daxu.swamp.test.rule;

import de.daxu.swamp.common.time.Dates;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class DateRule extends ExternalResource {

    public static DateRule now() {
        return new DateRule(Dates.now());
    }

    private final Logger logger = LoggerFactory.getLogger(DateRule.class);
    private LocalDateTime date;

    private DateRule(LocalDateTime date) {
        this.date = date;
    }

    @Override
    protected void before() throws Exception {
        logger.info("Time is fixed at {} ", date);
        Dates.useFixedClockAt(date);
    }
}