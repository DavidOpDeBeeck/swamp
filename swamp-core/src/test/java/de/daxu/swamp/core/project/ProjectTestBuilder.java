package de.daxu.swamp.core.project;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project.Builder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ProjectTestBuilder {

    private String name = "a project name";
    private String description = "a project description";
    private LocalDateTime createdAt = LocalDateTime.now();
    private Set<Container> containers = newHashSet();

    public static ProjectTestBuilder aProject() {
        return new ProjectTestBuilder();
    }

    public ProjectTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ProjectTestBuilder withDescription( String description ) {
        this.description = description;
        return this;
    }

    public ProjectTestBuilder createdAt( LocalDateTime created ) {
        this.createdAt = created;
        return this;
    }

    public ProjectTestBuilder withContainer( Container... containers ) {
        Collections.addAll( this.containers, containers );
        return this;
    }

    public Project build() {
        return Builder.aProject()
                .withName( name )
                .withDescription( description )
                .withCreatedAt( createdAt )
                .withContainers( containers )
                .build();
    }
}