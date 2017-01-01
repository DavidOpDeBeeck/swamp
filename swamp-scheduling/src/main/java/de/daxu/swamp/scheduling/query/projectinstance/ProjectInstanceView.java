package de.daxu.swamp.scheduling.query.projectinstance;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table( name = "project_instance_view" )
@SuppressWarnings( "unused" )
public class ProjectInstanceView extends EntityView {

    @Embedded
    @NotNull( message = "{NotNull.ProjectInstanceView.projectInstanceId}" )
    private ProjectInstanceId projectInstanceId;

    @Column( name = "name" )
    private String name;

    @Column( name = "description" )
    private String description;

    @Column( name = "initialized_at" )
    private LocalDateTime initializedAt;

    @ElementCollection
    @CollectionTable( name = "project_instance_container_view", joinColumns = @JoinColumn( name = "project_instance_view_id" ) )
    private Set<ContainerInstanceId> containers;

    private ProjectInstanceView() {
    }

    public ProjectInstanceView( ProjectInstanceId projectInstanceId,
                                String name,
                                String description,
                                LocalDateTime initializedAt,
                                Set<ContainerInstanceId> containers ) {
        this.projectInstanceId = projectInstanceId;
        this.name = name;
        this.description = description;
        this.initializedAt = initializedAt;
        this.containers = containers;
    }

    public ProjectInstanceId getProjectInstanceId() {
        return projectInstanceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @JsonSerialize( using = LocalDateTimeSerializer.class )
    public LocalDateTime getInitializedAt() {
        return initializedAt;
    }

    public Set<ContainerInstanceId> getContainers() {
        return containers;
    }

    public static class ProjectInstanceViewBuilder {

        private ProjectInstanceId projectInstanceId;
        private String name;
        private String description;
        private LocalDateTime initializedAt;
        private Set<ContainerInstanceId> containers;

        public static ProjectInstanceViewBuilder aProjectInstanceView() {
            return new ProjectInstanceViewBuilder();
        }

        public ProjectInstanceViewBuilder withProjectInstanceId( ProjectInstanceId projectInstanceId ) {
            this.projectInstanceId = projectInstanceId;
            return this;
        }

        public ProjectInstanceViewBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ProjectInstanceViewBuilder withDescription( String description ) {
            this.description = description;
            return this;
        }

        public ProjectInstanceViewBuilder withInitializedAt( LocalDateTime dateInitialized ) {
            this.initializedAt = dateInitialized;
            return this;
        }

        public ProjectInstanceViewBuilder withContainers( Set<ContainerInstanceId> containers ) {
            this.containers = containers;
            return this;
        }

        public ProjectInstanceView build() {
            return new ProjectInstanceView(
                    projectInstanceId,
                    name,
                    description,
                    initializedAt,
                    containers );
        }
    }
}
