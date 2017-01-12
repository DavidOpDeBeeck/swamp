package de.daxu.swamp.core.container;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainer;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProject;
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
        project = aProject().build();
        integration.save( project );
    }

    @Test
    public void addContainerToProject() throws Exception {
        Container container = aContainer().build();

        containerService.addContainerToProject( project, container );

        project = integration.find( project.getId(), Project.class );
        assertThat( project.getContainers() )
                .containsExactly( container );
    }

    @Test
    public void updateContainer() throws Exception {
        Container container = aContainer().withAliases( newHashSet( "oldName" ) ).build();
        project.addContainer( container );
        integration.save( container, project );

        container.setAliases( newHashSet("updatedName") );
        containerService.updateContainer( container );

        Container actual = integration.find( container.getId(), Container.class );
        assertThat( actual.getAliases() )
                .isEqualTo( container.getAliases() );
    }

    @Test
    public void removeContainerFromProject() throws Exception {
        Container container = aContainer().build();
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
        Container expected = aContainer().build();

        containerService.addContainerToProject( project, expected );

        Container actual = containerService.getContainer( expected.getId() );
        assertThat( actual )
                .isEqualTo( expected );
    }
}