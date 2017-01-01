package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceLogReceivedEvent extends ContainerInstanceEvent {

    private final String log;
    private final LocalDateTime logReceivedAt;

    public ContainerInstanceLogReceivedEvent( ContainerInstanceId containerInstanceId, String log, LocalDateTime logReceivedAt ) {
        super( containerInstanceId );
        this.log = log;
        this.logReceivedAt = logReceivedAt;
    }

    public String getLog() {
        return log;
    }

    public LocalDateTime getLogReceivedAt() {
        return logReceivedAt;
    }
}
