package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ReceiveContainerInstanceLogCommand extends ContainerInstanceCommand {

    private final String log;
    private final Date dateLogReceived;

    public ReceiveContainerInstanceLogCommand( ContainerInstanceId containerInstanceId, String log, Date dateLogReceived ) {
        super(containerInstanceId);
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
