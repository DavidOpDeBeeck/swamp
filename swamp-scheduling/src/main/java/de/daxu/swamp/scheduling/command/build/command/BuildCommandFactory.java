package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.build.BuildId;
import org.springframework.stereotype.Component;

@Component
public class BuildCommandFactory {

    public InitializeBuildCommand createInitializeCommand(Project project ) {
        return new InitializeBuildCommand( BuildId.random(), project );
    }
}
