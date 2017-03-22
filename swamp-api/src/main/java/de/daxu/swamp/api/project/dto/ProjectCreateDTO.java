package de.daxu.swamp.api.project.dto;

import org.hibernate.validator.constraints.NotBlank;

public class ProjectCreateDTO {

    @NotBlank(message = "{NotBlank.ProjectCreateDTO.name}")
    private String name;
    @NotBlank(message = "{NotBlank.ProjectCreateDTO.description}")
    private String description;

    @SuppressWarnings("unused")
    private ProjectCreateDTO() {
    }

    private ProjectCreateDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {

        private String name;
        private String description;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProjectCreateDTO build() {
            return new ProjectCreateDTO(name, description);
        }
    }

}
