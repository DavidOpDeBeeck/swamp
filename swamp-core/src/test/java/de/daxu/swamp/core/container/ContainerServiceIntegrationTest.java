package de.daxu.swamp.core.container;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainerTestBuilder;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule( spring );

    private Project project;
    private ContainerService containerService = spring.getInstance( ContainerService.class );

    @Before
    public void setUp() throws Exception {
        project = aProjectTestBuilder().build();
        integration.save( project );
    }

    @Test
    public void addContainerToProject() throws Exception {
        Container container = aContainerTestBuilder().build();

        containerService.addContainerToProject( project, container );

        project = integration.find( project.getId(), Project.class );
        assertThat( project.getContainers() )
                .containsExactly( container );
    }

    @Test
    public void updateContainer() throws Exception {
        Container container = aContainerTestBuilder().withName( "oldName" ).build();
        project.addContainer( container );
        integration.save( container, project );

        container.setName( "updatedName" );
        containerService.updateContainer( container );

        Container actual = integration.find( container.getId(), Container.class );
        assertThat( actual.getName() )
                .isEqualTo( container.getName() );
    }

    @Test
    public void removeContainerFromProject() throws Exception {
        Container container = aContainerTestBuilder().build();
        project.addContainer( container );
        integration.save( container, project );

        containerService.removeContainerFromProject( project, container );

        project = integration.find( project.getId(), Project.class );
        Container deletedContainer = integration.find( container.getId(), Container.class );
        assertThat( deletedContainer ).isNull();
        assertThat( project.getContainers() )
                .doesNotContain( container );
    }

    @Test
    public void getContainer() throws Exception {
        Container expected = aContainerTestBuilder().build();

        containerService.addContainerToProject( project, expected );

        Container actual = containerService.getContainer( expected.getId() );
        assertThat( actual )
                .isEqualTo( expected );
    }
}