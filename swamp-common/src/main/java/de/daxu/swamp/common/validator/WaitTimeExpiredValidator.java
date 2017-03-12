package de.daxu.swamp.common.validator;

import java.time.LocalDateTime;
import java.util.function.Function;

public class WaitTimeExpiredValidator<T> implements Validator<T> {

    private final int waitTime;
    private final Function<T, LocalDateTime> dateTimeFunction;

    public WaitTimeExpiredValidator(int waitTime, Function<T, LocalDateTime> dateTimeFunction) {
        this.waitTime = waitTime;
        this.dateTimeFunction = dateTimeFunction;
    }

    @Override
    public boolean isValid(T object) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = dateTimeFunction.apply(object);
        LocalDateTime timePlusWaitTime = time.plusSeconds(waitTime);
        return now.isAfter(timePlusWaitTime);
    }
}
