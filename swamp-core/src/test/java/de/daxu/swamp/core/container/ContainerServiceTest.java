package de.daxu.swamp.core.container;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.integration.ServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainerTestBuilder;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerServiceTest extends ServiceIntegrationTest {

    @Autowired
    private ContainerService containerService;

    @Test
    public void addContainerToProject() throws Exception {
        Project project = entityManager().persist( aProjectTestBuilder().build() );
        Container container = aContainerTestBuilder().build();

        containerService.addContainerToProject( project, container );
        project = entityManager().find( Project.class, project.getId() );

        assertThat( project.getContainers() )
                .usingDefaultElementComparator()
                .containsOnly( container );
    }
}