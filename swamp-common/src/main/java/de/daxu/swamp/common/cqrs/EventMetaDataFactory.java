package de.daxu.swamp.common.cqrs;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventMetaDataFactory {

    public EventMetaData create() {
        return new EventMetaData(LocalDateTime.now());
    }
}
