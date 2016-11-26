package de.daxu.swamp.scheduling.write;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.command.ContainerInstanceCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerInstanceWriteService {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ContainerInstanceCommandFactory containerInstanceCommandFactory;

    public void schedule( Container container, Server server ) {
        commandGateway.send( containerInstanceCommandFactory.createScheduleCommand( container, server ) );
    }

    public void create( ContainerInstanceId containerInstanceId, String internalContainerId, String internalContainerName ) {
        commandGateway.send( containerInstanceCommandFactory.createCreateCommand( containerInstanceId, internalContainerId, internalContainerName ) );
    }

    public void start( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStartCommand( containerInstanceId ) );
    }

    public void startLogging( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStartLoggingCommand( containerInstanceId ) );
    }

    public void receiveLog( ContainerInstanceId containerInstanceId, String log ) {
        commandGateway.send( containerInstanceCommandFactory.createReceiveLogCommand( containerInstanceId, log ) );
    }
}
