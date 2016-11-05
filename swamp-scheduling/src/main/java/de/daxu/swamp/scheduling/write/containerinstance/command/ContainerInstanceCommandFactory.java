package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ContainerInstanceCommandFactory {

    public CreateContainerInstanceCommand createCommand( ContainerInstanceId containerInstanceId,
                                                         String internalContainerId,
                                                         String internalContainerName,
                                                         Server server ) {
        return new CreateContainerInstanceCommand( containerInstanceId, internalContainerId, internalContainerName, new Date(), server );
    }

    public StartContainerInstanceCommand startCommand( ContainerInstanceId containerInstanceId ) {
        return new StartContainerInstanceCommand( containerInstanceId, new Date() );
    }

}
