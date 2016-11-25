package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ContainerInstanceCommandFactory {

    public ScheduleContainerInstanceCommand createScheduleCommand( Container container, Server server ) {
        return new ScheduleContainerInstanceCommand( ContainerInstanceId.random(), container, server, new Date() );
    }

    public CreateContainerInstanceCommand createCreateCommand( ContainerInstanceId containerInstanceId,
                                                               String internalContainerId,
                                                               String internalContainerName ) {
        return new CreateContainerInstanceCommand( containerInstanceId, internalContainerId, internalContainerName, new Date() );
    }

    public StartContainerInstanceCommand createStartCommand( ContainerInstanceId containerInstanceId ) {
        return new StartContainerInstanceCommand( containerInstanceId, new Date() );
    }

}
