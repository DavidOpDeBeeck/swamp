package de.daxu.swamp.core.container;

import de.daxu.swamp.common.Identifiable;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "project" )
public class Project extends Identifiable {

    @NotBlank( message = "{NotBlank.Project.name}" )
    @Column( name = "name" )
    private String name;

    @NotBlank( message = "{NotBlank.Project.description}" )
    @Lob
    @Column( name = "description" )
    private String description;

    @NotNull( message = "{NotNull.Project.created}" )
    @Column( name = "created" )
    private Date created;

    @OneToMany( fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "project_id", referencedColumnName = "id" )
    private List<Container> containers;

    public void setName( String name ) {
        this.name = name;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    private Project() {
    }

    private Project( String name, String description, Date created, List<Container> containers ) {
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

    public List<Container> getContainers() {
        return containers;
    }

    public boolean addContainer( Container container ) {
        if ( this.containers == null )
            this.containers = new ArrayList<>();
        return this.containers.add( container );
    }

    public boolean removeContainer( Container container ) {
        return this.containers.remove( container );
    }

    public static class ProjectBuilder {

        private String name;
        private String description;
        private Date created;
        private List<Container> containers;

        public static ProjectBuilder aProject() {
            return new ProjectBuilder();
        }

        ProjectBuilder() {
            this.containers = new ArrayList<>();
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
