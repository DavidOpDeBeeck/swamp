package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.ProjectCreateDTO;
import de.daxu.swamp.core.project.Project;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

import static de.daxu.swamp.core.project.Project.ProjectBuilder.aProjectBuilder;

@Component
public class ProjectCreateConverter implements DomainConverter<ProjectCreateDTO, Project> {

    @Override
    public Project toDomain( ProjectCreateDTO dto ) {
        return aProjectBuilder()
                .withName( dto.name )
                .withDescription( dto.description )
                .createdAt( LocalDateTime.now( Clock.systemUTC()) )
                .build();
    }
}
