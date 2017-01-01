package de.daxu.swamp.api.project.dto;

public class ProjectCreateDTOTestBuilder {

    private String name = "a project name";
    private String description = "a project description";

    public static ProjectCreateDTOTestBuilder aProjectCreateDTO() {
        return new ProjectCreateDTOTestBuilder();
    }

    public ProjectCreateDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ProjectCreateDTOTestBuilder withDescription( String description ) {
        this.description = description;
        return this;
    }

    public ProjectCreateDTO build() {
        ProjectCreateDTO dto = new ProjectCreateDTO();
        dto.name = name;
        dto.description = description;
        return dto;
    }
}