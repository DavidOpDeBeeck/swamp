package de.daxu.swamp.core.project;

import de.daxu.swamp.common.jpa.Identifiable;
import de.daxu.swamp.core.container.Container;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

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

    @NotNull( message = "{NotNull.Project.createdAt}" )
    @Column( name = "created_at" )
    private LocalDateTime createdAt;

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

    private Project( String name, String description, LocalDateTime createdAt, Set<Container> containers ) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.containers = containers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Container> getContainers() {
        return containers;
    }

    public boolean addContainer( Container container ) {
        if ( this.containers == null ) this.containers = newHashSet();
        return this.containers.add( container );
    }

    public boolean removeContainer( Container container ) {
        return this.containers.remove( container );
    }

    public static class Builder {

        private String name;
        private String description;
        private LocalDateTime createdAt;
        private Set<Container> containers;

        public static Builder aProject() {
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

        public Builder withCreatedAt( LocalDateTime createdAt ) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withContainers( Set<Container> containers ) {
            this.containers = containers;
            return this;
        }

        public Project build() {
            return new Project( name, description, createdAt, containers );
        }
    }
}
