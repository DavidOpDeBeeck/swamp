package de.daxu.swamp.common.cqrs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class EventMetaData {

    private LocalDateTime createdAt;

    public EventMetaData(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
