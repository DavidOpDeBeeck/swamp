package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table( name = "project_view" )
@SuppressWarnings( "unused" )
public class ProjectView extends EntityView {

    @Column( name = "name" )
    private String name;

    @Column( name = "description" )
    private String description;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn( name = "project_view_id", referencedColumnName = "id" )
    private Set<ProjectInstanceView> projectInstances;

    private ProjectView() {
    }

    private ProjectView( String name, String description, Set<ProjectInstanceView> projectInstances ) {
        this.name = name;
        this.description = description;
        this.projectInstances = projectInstances;
    }

    void addProjectInstance( ProjectInstanceView projectInstance ) {
        if ( this.projectInstances == null ) this.projectInstances = newHashSet();
        this.projectInstances.add( projectInstance );
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<ProjectInstanceView> getProjectInstances() {
        return projectInstances;
    }

    public static class Builder {

        private String name;
        private String description;
        private Set<ProjectInstanceView> projectInstances;

        public static Builder aProjectView() {
            return new Builder();
        }

        public Builder withName( String name ) {
            this.name = name;
            return this;
        }

        public Builder withDescription( String description ) {
            this.description = description;
            return this;
        }

        public Builder withProjectInstances( Set<ProjectInstanceView> projectInstances ) {
            this.projectInstances = projectInstances;
            return this;
        }

        public ProjectView build() {
            return new ProjectView( name, description, projectInstances );
        }
    }
}
