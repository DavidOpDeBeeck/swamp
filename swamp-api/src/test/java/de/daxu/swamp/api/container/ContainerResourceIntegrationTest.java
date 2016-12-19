package de.daxu.swamp.api.container;

import de.daxu.swamp.api.container.converter.ContainerConverter;
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

import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainerTestBuilder;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
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

        resource.setPathPrefixes( "/projects/", project.getId() );
        containerConverter = spring.getInstance( ContainerConverter.class );
    }

    @Test
    public void getAll() throws Exception {
        Container container1 = aContainerTestBuilder().build();
        Container container2 = aContainerTestBuilder().build();

        addContainer( container1 );
        addContainer( container2 );

        List<ContainerDTO> containers = resource.getList( ContainerDTO.class, "/containers" );

        assertThat( containers ).isNotEmpty();
        assertThat( containers )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        containerConverter.toDTO( container1 ),
                        containerConverter.toDTO( container2 ) );
    }

    @Test
    public void post() throws Exception {
        Container expected = aContainerTestBuilder().build();

        String id = resource.post( expected, "/", "containers" );
        Container actual = resource.find( id, Container.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "name", "runConfiguration" );
    }

    @Test
    public void get() throws Exception {
        Container expected = aContainerTestBuilder().build();
        addContainer( expected );

        ContainerDTO actual = resource.get( ContainerDTO.class, "/containers/", expected.getId() );

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

        resource.put( expected, "/containers/", container.getId() );
        Container actual = resource.find( container.getId(), Container.class );

        assertThat( actual ).isNotNull();
        assertThat( actual.getName() ).isEqualTo( expected.getName() );
    }

    @Test
    public void delete() throws Exception {
        Container expected = aContainerTestBuilder().build();
        addContainer( expected );

        resource.delete( "/containers/", expected.getId() );
        Container actual = resource.find( expected.getId(), Container.class );

        assertThat( actual ).isNull();
    }

    private void addContainer( Container container ) {
        resource.save( container );
        project.addContainer( container );
        resource.save( project );
    }
}