package de.daxu.swamp.core.container;

import de.daxu.swamp.common.jpa.Identifiable;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "project" )
@SuppressWarnings( "unused" )
public class Project extends Identifiable {

    @NotBlank( message = "{NotBlank.Project.name}" )
    @Column( name = "name" )
    private String name;

    @NotBlank( message = "{NotBlank.Project.description}" )
    @Lob
    @Column( name = "description" )
    private String description;

    @NotNull( message = "{NotNull.Project.withCreated}" )
    @Column( name = "created" )
    private Date created;

    @OneToMany( fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "project_id", referencedColumnName = "id" )
    private Set<Container> containers;

    public void setName( String name ) {
        this.name = name;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    private Project() {
    }

    private Project( String name, String description, Date created, Set<Container> containers ) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.containers = containers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public Set<Container> getContainers() {
        return containers;
    }

    public boolean addContainer( Container container ) {
        if ( this.containers == null )
            this.containers = new HashSet<>();
        return this.containers.add( container );
    }

    public boolean removeContainer( Container container ) {
        return this.containers.remove( container );
    }

    public static class ProjectBuilder {

        private String name;
        private String description;
        private Date created;
        private Set<Container> containers;

        public static ProjectBuilder aProject() {
            return new ProjectBuilder();
        }

        ProjectBuilder() {
            this.containers = new HashSet<>();
        }

        public ProjectBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ProjectBuilder withDescription( String description ) {
            this.description = description;
            return this;
        }

        public ProjectBuilder createdAt( Date created ) {
            this.created = created;
            return this;
        }

        public ProjectBuilder withContainer( Container... containers ) {
            Collections.addAll( this.containers, containers );
            return this;
        }

        public Project build() {
            return new Project( name, description, created, containers );
        }
    }
}
