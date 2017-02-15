package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.build.command.BuildCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildCommandService {

    private final CommandGateway commandGateway;
    private final BuildCommandFactory buildCommandFactory;

    @Autowired
    public BuildCommandService(CommandGateway commandGateway,
                               BuildCommandFactory buildCommandFactory) {
        this.commandGateway = commandGateway;
        this.buildCommandFactory = buildCommandFactory;
    }

    public void initialize( Project project, int sequence ) {
        commandGateway.send( buildCommandFactory.createInitializeCommand( project, sequence ) );
    }
}


