package de.daxu.swamp.core.project;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project.Builder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static de.daxu.swamp.core.ProjectTestConstants.Project.*;

public class ProjectTestBuilder {

    private String name = PROJECT_NAME;
    private String description = PROJECT_DESCRIPTION;
    private LocalDateTime createdAt = PROJECT_CREATED_AT;
    private Set<Container> containers = PROJECT_CONTAINERS;

    public static ProjectTestBuilder aProject() {
        return new ProjectTestBuilder();
    }

    public static ProjectTestBuilder anotherProject() {
        return new ProjectTestBuilder()
                .withName(ANOTHER_PROJECT_NAME)
                .withDescription(ANOTHER_PROJECT_DESCRIPTION)
                .withCreatedAt(ANOTHER_PROJECT_CREATED_AT)
                .withContainers(ANOTHER_PROJECT_CONTAINERS);
    }

    public ProjectTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProjectTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectTestBuilder withCreatedAt(LocalDateTime created) {
        this.createdAt = created;
        return this;
    }

    public ProjectTestBuilder withContainer(Container... containers) {
        Collections.addAll(this.containers, containers);
        return this;
    }

    public ProjectTestBuilder withContainers(Set<Container> containers) {
        this.containers = containers;
        return this;
    }

    public Project build() {
        return Builder.aProject()
                .withName(name)
                .withDescription(description)
                .withCreatedAt(createdAt)
                .withContainers(new HashSet<>(containers))
                .build();
    }
}