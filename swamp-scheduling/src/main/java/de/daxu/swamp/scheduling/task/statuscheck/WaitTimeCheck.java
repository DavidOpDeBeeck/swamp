package de.daxu.swamp.scheduling.task.statuscheck;

import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;

import java.time.LocalDateTime;
import java.util.function.Function;

public class WaitTimeCheck implements StatusChangeCheck {

    private final int waitTime;
    private final Function<ContainerInstanceView, LocalDateTime> dateTimeFunction;

    public WaitTimeCheck( int waitTime, Function<ContainerInstanceView, LocalDateTime> dateTimeFunction ) {
        this.waitTime = waitTime;
        this.dateTimeFunction = dateTimeFunction;
    }

    @Override
    public boolean check( ContainerInstanceView containerInstanceView ) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = dateTimeFunction.apply( containerInstanceView );
        LocalDateTime timePlusWaitTime = time.plusSeconds( waitTime );
        return now.isAfter( timePlusWaitTime );
    }
}
