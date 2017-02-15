package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.scheduling.query.build.BuildView;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table( name = "project_view" )
@SuppressWarnings( "unused" )
public class ProjectView extends EntityView {

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

    private ProjectView( String name, String description, int buildSequence, Set<BuildView> builds) {
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

        private String name;
        private String description;
        private int buildSequence;
        private Set<BuildView> projectInstances;

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

        public Builder withBuildSequence( int buildSequence ) {
            this.buildSequence = buildSequence;
            return this;
        }

        public Builder withBuilds(Set<BuildView> projectInstances ) {
            this.projectInstances = projectInstances;
            return this;
        }

        public ProjectView build() {
            return new ProjectView( name, description, buildSequence, projectInstances );
        }
    }
}
