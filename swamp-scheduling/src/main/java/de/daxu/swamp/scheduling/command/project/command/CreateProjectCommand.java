package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.scheduling.command.project.ProjectId;

public class CreateProjectCommand extends ProjectCommand {

    private final String name;
    private final String description;

    public CreateProjectCommand(ProjectId projectId,
                                String name,
                                String description) {
        super(projectId);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
