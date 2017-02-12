package de.daxu.swamp.common.cqrs;

import java.time.LocalDateTime;

public class EventMetaData {

    private LocalDateTime createdAt;

    public EventMetaData(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
