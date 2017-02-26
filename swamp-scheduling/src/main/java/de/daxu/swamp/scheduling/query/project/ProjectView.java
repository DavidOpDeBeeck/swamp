package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import de.daxu.swamp.scheduling.query.build.BuildView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table( name = "project_view" )
@SuppressWarnings( "unused" )
public class ProjectView extends EntityView {

    @Embedded
    @NotNull(message = "{NotNull.ProjectView.projectId}")
    private ProjectId projectId;

    @Column( name = "name", unique = true)
    private String name;

    @Column( name = "description" )
    private String description;

    @Column( name = "build_sequence" )
    private Integer buildSequence;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn( name = "project_view_id", referencedColumnName = "id" )
    private Set<BuildView> builds;

    private ProjectView() {
    }

    private ProjectView( ProjectId projectId, String name, String description, int buildSequence, Set<BuildView> builds) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.buildSequence = buildSequence;
        this.builds = builds;
    }

    public void setBuildSequence(int buildSequence) {
        this.buildSequence = buildSequence;
    }

    void addBuild(BuildView build ) {
        if ( this.builds == null ) this.builds = newHashSet();
        this.builds.add( build );
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBuildSequence() {
        return buildSequence;
    }

    public Set<BuildView> getBuilds() {
        return builds;
    }

    public static class Builder {

        private ProjectId projectId;
        private String name;
        private String description;
        private int buildSequence;
        private Set<BuildView> projectInstances;

        public static Builder aProjectView() {
            return new Builder();
        }

        public Builder withProjectId( ProjectId projectId ) {
            this.projectId = projectId;
            return this;
        }

        public Builder withName( String name ) {
            this.name = name;
            return this;
        }

        public Builder withDescription( String description ) {
            this.description = description;
            return this;
        }

        public Builder withBuildSequence( int buildSequence ) {
            this.buildSequence = buildSequence;
            return this;
        }

        public Builder withBuilds(Set<BuildView> projectInstances ) {
            this.projectInstances = projectInstances;
            return this;
        }

        public ProjectView build() {
            return new ProjectView( projectId, name, description, buildSequence, projectInstances );
        }
    }
}
