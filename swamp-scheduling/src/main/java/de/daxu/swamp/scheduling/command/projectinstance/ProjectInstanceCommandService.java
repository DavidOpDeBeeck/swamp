package de.daxu.swamp.scheduling.command.projectinstance;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.projectinstance.command.ProjectInstanceCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectInstanceCommandService {

    private final CommandGateway commandGateway;
    private final ProjectInstanceCommandFactory projectInstanceCommandFactory;

    @Autowired
    public ProjectInstanceCommandService( CommandGateway commandGateway,
                                          ProjectInstanceCommandFactory projectInstanceCommandFactory ) {
        this.commandGateway = commandGateway;
        this.projectInstanceCommandFactory = projectInstanceCommandFactory;
    }

    public void initialize( Project project ) {
        commandGateway.send( projectInstanceCommandFactory.createInitializeCommand( project ) );
    }
}


