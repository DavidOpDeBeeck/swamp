package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    public final static String SCHEDULING_URL = "/scheduling";

    private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(value = "/container", method = RequestMethod.GET)
    public void schedule() {
        ContainerInstanceId containerInstanceId = ContainerInstanceId.random();
        commandGateway.send(new CreateContainerInstanceCommand( containerInstanceId, "centos" ));
    }
}
