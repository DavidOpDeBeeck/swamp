package de.daxu.swamp.scheduling.write;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteService {

    @Autowired
    private CommandGateway commandGateway;

    public void startContainer( ContainerInstanceId containerInstanceId ) {
        commandGateway.send(new CreateContainerInstanceCommand( containerInstanceId, "centos" ));
    }

}
