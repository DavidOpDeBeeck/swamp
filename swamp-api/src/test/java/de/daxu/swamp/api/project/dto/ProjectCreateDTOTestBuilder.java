package de.daxu.swamp.api.project.dto;

import static de.daxu.swamp.core.ProjectTestConstants.Project.*;

public class ProjectCreateDTOTestBuilder {

    private String name = PROJECT_NAME;
    private String description = PROJECT_DESCRIPTION;

    public static ProjectCreateDTOTestBuilder aProjectCreateDTO() {
        return new ProjectCreateDTOTestBuilder();
    }

    public static ProjectCreateDTOTestBuilder anotherProjectCreateDTO() {
        return new ProjectCreateDTOTestBuilder()
                .withName(ANOTHER_PROJECT_NAME)
                .withDescription(ANOTHER_PROJECT_DESCRIPTION);
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