package de.daxu.swamp.scheduling.write;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.command.ContainerInstanceCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteService {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ContainerInstanceCommandFactory containerInstanceCommandFactory;

    public void startContainer( ContainerInstanceId containerInstanceId, Server server ) {
        containerInstanceCommandFactory.createCommand( containerInstanceId, "id", "name", server );
    }

}
