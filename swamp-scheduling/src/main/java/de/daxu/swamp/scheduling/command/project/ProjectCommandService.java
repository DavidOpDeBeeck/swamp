package de.daxu.swamp.scheduling.command.project;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.command.ProjectCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProjectCommandService {

    private final CommandGateway gateway;
    private final ProjectCommandFactory factory;

    @Autowired
    public ProjectCommandService(CommandGateway gateway,
                                 ProjectCommandFactory factory) {
        this.gateway = gateway;
        this.factory = factory;
    }

    public void createProject(String id, String name, String description) {
        gateway.send(factory.createProjectCommand(ProjectId.from(id), name, description));
    }

    public void scheduleBuild(ProjectId projectId, Set<Container> containers) {
        gateway.send(factory.scheduleBuildCommand(projectId, containers));
    }

    void addBuild(ProjectId projectId, BuildId buildId) {
        gateway.send(factory.addBuildCommand(projectId, buildId));
    }
}