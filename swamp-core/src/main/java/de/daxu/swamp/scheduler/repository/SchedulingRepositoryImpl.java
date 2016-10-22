package de.daxu.swamp.scheduler.repository;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.ContainerInstance;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class SchedulingRepositoryImpl implements SchedulingRepository {

    private Map<Project, Collection<Container>> projectMap;
    private Set<ContainerInstance> instances;

    @PostConstruct
    public void setup() {
        this.projectMap = new HashMap<>();
        this.instances = new TreeSet<>( new ContainerInstanceComparator() );
    }

    public void addContainerToProject( Project project, Container container ) {
        Collection<Container> containers = projectMap.get( project ) != null ? projectMap.get( project ) : new ArrayList<>();
        containers.add( container );
        projectMap.put( project, containers );
    }

    public void addInstance( ContainerInstance instance ) {
        instances.add( instance );
    }

    public void updateInstance( ContainerInstance instance ) {
        instances.remove( instance );
        instances.add( instance );
    }

    public void removeInstance( ContainerInstance instance ) {
        instances.remove( instance );
    }

    @Override
    public ContainerInstance getInstance( String internalId ) {
        return instances.stream()
                .filter( i -> i.getInternalContainerId().equals( internalId ) )
                .findAny().orElse( null );
    }

    public Set<ContainerInstance> getAllInstances() {
        return instances;
    }

    public Set<Project> getAllProjects() {
        return projectMap.keySet();
    }

    public ContainerInstance getInstance( Container container ) {
        return instances.stream()
                .filter( i -> i.getContainer().equals( container ) )
                .findAny().orElse( null );
    }

    private class ContainerInstanceComparator implements Comparator<ContainerInstance> {

        @Override
        public int compare( ContainerInstance o1, ContainerInstance o2 ) {
            return o1.getInternalContainerId().compareTo( o2.getInternalContainerId() );
        }
    }
}
