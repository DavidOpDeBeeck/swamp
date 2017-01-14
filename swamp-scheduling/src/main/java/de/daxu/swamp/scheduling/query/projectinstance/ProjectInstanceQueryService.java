package de.daxu.swamp.scheduling.query.projectinstance;

import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectInstanceQueryService {

    private final ProjectInstanceViewRepository projectInstanceViewRepository;

    @Autowired
    public ProjectInstanceQueryService( ProjectInstanceViewRepository projectInstanceViewRepository ) {
        this.projectInstanceViewRepository = projectInstanceViewRepository;
    }

    public ProjectInstanceView getProjectInstanceViewById( ProjectInstanceId projectInstanceId ) {
        return projectInstanceViewRepository.getByProjectInstanceId( projectInstanceId );
    }
}
