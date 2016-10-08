package de.daxu.swamp.api.dto.scheduler;

import de.daxu.swamp.core.scheduler.ContainerInstance;

import java.util.Date;

public class ContainerInstanceDTO {

    public String projectId;
    public String containerId;
    public ContainerInstance.Status status;
    public Date startedAt;
    public Date finishedAt;
    public String logs;

}
