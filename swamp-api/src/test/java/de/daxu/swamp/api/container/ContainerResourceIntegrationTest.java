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

import static de.daxu.swamp.api.container.dto.ContainerCreateDTOTestBuilder.aContainerCreateDTOTestBuilder;
import static de.daxu.swamp.common.rest.RestClient.list;
import static de.daxu.swamp.common.rest.RestClient.type;
import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainerTestBuilder;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
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
    private ContainerConverter containerConverter;

    @Before
    public void setUp() throws Exception {
        project = aProjectTestBuilder().build();
        resource.save( project );
        containerConverter = spring.getInstance( ContainerConverter.class );
    }

    private String projectPath() {
        return format( "%s/%s", "projects", project.getId() );
    }

    @Test
    public void getAll() throws Exception {
        Container container1 = aContainerTestBuilder().build();
        Container container2 = aContainerTestBuilder().build();

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
        ContainerCreateDTO expected = aContainerCreateDTOTestBuilder().build();

        String id = resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .post( expected );

        Container actual = resource.find( id, Container.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "name", "runConfiguration.name" );
    }

    @Test
    public void get() throws Exception {
        Container expected = aContainerTestBuilder().build();
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
        Container container = aContainerTestBuilder()
                .withName( "old" )
                .build();

        addContainer( container );

        Container expected = aContainerTestBuilder()
                .withName( "updated" )
                .build();

        resource.webClient()
                .path( projectPath() )
                .path( "containers" )
                .path( container.getId() )
                .put( expected );

        Container actual = resource.find( container.getId(), Container.class );

        assertThat( actual ).isNotNull();
        assertThat( actual.getName() ).isEqualTo( expected.getName() );
    }

    @Test
    public void delete() throws Exception {
        Container expected = aContainerTestBuilder().build();
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