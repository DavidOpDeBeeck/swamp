package de.daxu.swamp.scheduler.scheduling;

import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.core.ProjectInstance;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class SchedulingRepositoryImpl implements SchedulingRepository {

    private Map<String, ProjectInstance> projectInstances = new HashMap<>();

    @Override
    public void addProjectInstance( ProjectInstance projectInstance ) {
        projectInstances.put( projectInstance.getId(), projectInstance );
    }

    @Override
    public void updateProjectInstance( ProjectInstance projectInstance ) {
        addProjectInstance( projectInstance );
    }

    @Override
    public ProjectInstance getProjectInstance( String id ) {
        return projectInstances.get( id );
    }

    @Override
    public Set<ProjectInstance> getAllProjectInstances() {
        return new HashSet<>( projectInstances.values() );
    }

    @Override
    public ContainerInstance getContainerInstanceByInternalId( String internalId ) {
        return projectInstances.values()
                .stream()
                .flatMap( p -> p.getContainerInstances().stream() )
                .filter( i -> i.getInternalContainerId().equals( internalId ) )
                .findFirst().orElse( null );
    }
}
