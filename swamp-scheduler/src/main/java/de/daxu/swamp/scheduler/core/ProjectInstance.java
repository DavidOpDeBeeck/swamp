package de.daxu.swamp.scheduler.core;

import de.daxu.swamp.common.jpa.Identifiable;
import de.daxu.swamp.core.container.Project;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProjectInstance extends Identifiable {

    private Project project;
    private Set<ContainerInstance> containerInstances;
    private Date startDate;

    private ProjectInstance( Project project, Set<ContainerInstance> containerInstances, Date startDate ) {
        this.id = UUID.randomUUID().toString();
        this.project = project;
        this.containerInstances = containerInstances;
        this.startDate = startDate;
    }

    public boolean addContainerInstance( ContainerInstance containerInstance ) {
        return this.containerInstances.add( containerInstance );
    }

    public boolean updateContainerInstance( ContainerInstance containerInstance ) {
        return this.containerInstances.remove( containerInstance ) && this.containerInstances.add( containerInstance );
    }

    public boolean removeContainerInstance( ContainerInstance containerInstance ) {
        return this.containerInstances.remove( containerInstance );
    }

    public ContainerInstance getContainerInstance( String id ) {
        return this.containerInstances.stream()
                .filter( i -> i.getId().equals( id ) )
                .findFirst().orElse( null );
    }

    public Project getProject() {
        return project;
    }

    public Set<ContainerInstance> getContainerInstances() {
        return containerInstances;
    }

    public Date getStartDate() {
        return startDate;
    }

    public static class ProjectInstanceBuilder {

        private Project project;
        private Set<ContainerInstance> containerInstances;
        private Date startDate;

        private ProjectInstanceBuilder() {
            containerInstances = new HashSet<>(  );
        }

        public static ProjectInstanceBuilder aProjectInstance() {
            return new ProjectInstanceBuilder();
        }

        public ProjectInstanceBuilder withProject( Project project ) {
            this.project = project;
            return this;
        }

        public ProjectInstanceBuilder withContainerInstances( Set<ContainerInstance> containerInstances ) {
            this.containerInstances = containerInstances;
            return this;
        }

        public ProjectInstanceBuilder withStartDate( Date startDate ) {
            this.startDate = startDate;
            return this;
        }

        public ProjectInstance build() {
            return new ProjectInstance( project, containerInstances, startDate == null ? new Date() : startDate );
        }
    }

}
