package de.daxu.swamp.scheduling.command.projectinstance.command;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;
import org.springframework.stereotype.Component;

@Component
public class ProjectInstanceCommandFactory {

    public InitializeProjectInstanceCommand createInitializeCommand( Project project ) {
        return new InitializeProjectInstanceCommand( ProjectInstanceId.random(), project );
    }
}
