package de.daxu.swamp.scheduler.command.instance;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.event.EventHandler;

import static de.daxu.swamp.scheduler.client.DockerClientFactory.createClient;

public class RemoveCommand extends Command<ContainerInstance> {

    public RemoveCommand( EventHandler eventHandler ) {
        super( eventHandler );
    }

    @Override
    public void execute( ContainerInstance instance ) {
        createClient( instance.getServer() )
                .removeContainerCmd( instance.getInternalContainerId() )
                .exec();
    }
}
