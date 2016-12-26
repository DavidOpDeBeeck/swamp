package de.daxu.swamp.scheduling.command.projectinstance.event;

import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;

public class ProjectInstanceEvent {

    private ProjectInstanceId projectInstanceId;

    public ProjectInstanceEvent( ProjectInstanceId projectInstanceId ) {
        this.projectInstanceId = projectInstanceId;
    }

    public ProjectInstanceId getProjectInstanceId() {
        return projectInstanceId;
    }
}
