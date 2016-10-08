package de.daxu.swamp.api.dto.scheduler;

import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.api.dto.location.ServerDTO;
import de.daxu.swamp.core.scheduler.ContainerInstance;

import java.util.Date;

public class ContainerInstanceDTO {

    public ContainerInstance.Status status;
    public Date startedAt;
    public Date finishedAt;
    public ServerDTO server;
    public ContainerDTO containerId;
    public String logs;

}
