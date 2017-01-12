package de.daxu.swamp.api.container;

import de.daxu.swamp.api.container.converter.ContainerConverter;
import de.daxu.swamp.api.container.dto.ContainerCreateDTO;
import de.daxu.swamp.api.container.dto.ContainerDTO;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.api.container.dto.ContainerCreateDTOTestBuilder.aContainerCreateDTO;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.common.web.WebClient.type;
import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainer;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProject;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class ContainerResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    private Project project;
    private ContainerConverter containerConverter = spring.getInstance( ContainerConverter.class );

    @Before
    public void setUp() throws Exception {
        project = aProject().build();
        resource.save( project );
    }

    private String projectPath() {
        return format( "%s/%s", "projects", project.getId() );
    }

    @Test
    public void getAll() throws Exception {
        Container container1 = aContainer().build();
        Container container2 = aContainer().build();

        addContainer( container1 );
        addContainer( container2 );

        List<ContainerDTO> containers = resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .type( list( ContainerDTO.class ) )
                .get();

        assertThat( containers ).isNotEmpty();
        assertThat( containers )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        containerConverter.toDTO( container1 ),
                        containerConverter.toDTO( container2 ) );
    }

    @Test
    public void post() throws Exception {
        ContainerCreateDTO expected = aContainerCreateDTO().build();

        String id = resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .post( expected );

        Container actual = resource.find( id, Container.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "aliases", "runConfiguration.name" );
    }

    @Test
    public void get() throws Exception {
        Container expected = aContainer().build();
        addContainer( expected );

        ContainerDTO actual = resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .path( expected.getId() )
                .type( type( ContainerDTO.class ) )
                .get();

        assertThat( actual ).isNotNull();
        assertReflectionEquals( actual, containerConverter.toDTO( expected ) );
    }

    @Test
    public void put() throws Exception {
        Container container = aContainer()
                .withAliases( newHashSet(  "old") )
                .build();

        addContainer( container );

        Container expected = aContainer()
                .withAliases( newHashSet("updated") )
                .build();

        resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .path( container.getId() )
                .put( expected );

        Container actual = resource.find( container.getId(), Container.class );

        assertThat( actual ).isNotNull();
        assertThat( actual.getAliases() ).isEqualTo( expected.getAliases() );
    }

    @Test
    public void delete() throws Exception {
        Container expected = aContainer().build();
        addContainer( expected );

        resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .path( expected.getId() )
                .delete();

        Container actual = resource.find( expected.getId(), Container.class );

        assertThat( actual ).isNull();
    }

    private void addContainer( Container container ) {
        resource.save( container );
        project.addContainer( container );
        resource.save( project );
    }
}