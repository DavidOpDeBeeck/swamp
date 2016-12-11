package de.daxu.swamp.core.container;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContainerService {

    private final ProjectRepository projectRepository;
    private final ContainerRepository containerRepository;

    @Autowired
    public ContainerService( ProjectRepository projectRepository,
                             ContainerRepository containerRepository ) {
        this.projectRepository = projectRepository;
        this.containerRepository = containerRepository;
    }

    public Container addContainerToProject( Project project, Container container ) {
        containerRepository.save( container );
        project.addContainer( container );
        projectRepository.save( project );
        return container;
    }

    public Container updateContainer( Container container ) {
        return containerRepository.save( container );
    }

    public void removeContainerFromProject( Project project, Container container ) {
        containerRepository.delete( container );
        project.removeContainer( container );
        projectRepository.save( project );
    }

    public Container getContainer( String id ) {
        return containerRepository.findOne( id );
    }
}
