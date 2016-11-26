package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceLogReceivedEvent extends ContainerInstanceEvent {

    private final String log;
    private final Date dateLogReceived;

    public ContainerInstanceLogReceivedEvent( ContainerInstanceId containerInstanceId, String log, Date dateLogReceived ) {
        super( containerInstanceId );
        this.log = log;
        this.dateLogReceived = dateLogReceived;
    }

    public String getLog() {
        return log;
    }

    public Date getDateLogReceived() {
        return dateLogReceived;
    }
}
