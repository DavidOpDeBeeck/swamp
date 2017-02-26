package de.daxu.swamp.scheduling.command.project.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class ProjectBuildAddedEvent extends ProjectEvent {

    private final BuildId buildId;

    public ProjectBuildAddedEvent(ProjectId projectId,
                                  EventMetaData eventMetaData,
                                  BuildId buildId) {
        super(projectId, eventMetaData);
        this.buildId = buildId;
    }

    public BuildId getBuildId() {
        return buildId;
    }
}
