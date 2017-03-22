package de.daxu.swamp.api.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.ValueObject;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class ProjectDTO extends ValueObject {

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private int containers;

    @SuppressWarnings("unused")
    private ProjectDTO() {
    }

    private ProjectDTO(String id, String name, String description, LocalDateTime createdAt, int containers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.containers = containers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getContainers() {
        return containers;
    }

    public static class Builder {

        private String id;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private int containers;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withContainers(int containers) {
            this.containers = containers;
            return this;
        }

        public ProjectDTO build() {
            return new ProjectDTO(id, name, description, createdAt, containers);
        }
    }
}
