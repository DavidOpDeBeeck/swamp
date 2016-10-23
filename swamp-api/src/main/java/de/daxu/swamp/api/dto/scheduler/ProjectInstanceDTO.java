package de.daxu.swamp.api.dto.scheduler;

import de.daxu.swamp.api.dto.container.ProjectDTO;

import java.util.Date;

public class ProjectInstanceDTO {

    public String id;
    public ProjectDTO project;
    public int containers;
    public Date startedAt;

}
