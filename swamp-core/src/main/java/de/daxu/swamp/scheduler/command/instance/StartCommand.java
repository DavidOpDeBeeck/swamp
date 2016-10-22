package de.daxu.swamp.scheduler.command.instance;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.event.EventHandler;

import static de.daxu.swamp.scheduler.client.DockerClientFactory.createClient;

public class StartCommand extends Command<ContainerInstance> {

    public StartCommand( EventHandler eventHandler ) {
        super( eventHandler );
    }

    @Override
    public void execute( ContainerInstance instance ) {
        createClient( instance.getServer() )
                .startContainerCmd( instance.getInternalContainerId() )
                .exec();
    }
}
