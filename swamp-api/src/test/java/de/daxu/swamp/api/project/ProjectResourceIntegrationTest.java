package de.daxu.swamp.api.project;

import de.daxu.swamp.api.project.converter.ProjectConverter;
import de.daxu.swamp.api.project.dto.ProjectCreateDTO;
import de.daxu.swamp.api.project.dto.ProjectDTO;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.project.dto.ProjectCreateDTOTestBuilder.aProjectCreateDTOTestBuilder;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.common.web.WebClient.type;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    private ProjectConverter projectConverter;

    @Before
    public void setUp() throws Exception {
        projectConverter = spring.getInstance( ProjectConverter.class );
    }

    @Test
    public void getAll() throws Exception {
        Project project1 = aProjectTestBuilder().build();
        Project project2 = aProjectTestBuilder().build();
        resource.save( project1, project2 );

        List<ProjectDTO> projects = resource.webClient()
                .path( "projects" )
                .type( list( ProjectDTO.class ) )
                .get();

        assertThat( projects ).isNotEmpty();
        assertThat( projects )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        projectConverter.toDTO( project1 ),
                        projectConverter.toDTO( project2 ) );
    }

    @Test
    public void post() throws Exception {
        ProjectCreateDTO dto = aProjectCreateDTOTestBuilder().build();

        String id = resource.webClient()
                .path( "projects" )
                .post( dto );

        Project actual = resource.find( id, Project.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        dto, "name", "description" );
    }

    @Test
    public void get() throws Exception {
        Project expected = aProjectTestBuilder().build();
        resource.save( expected );

        ProjectDTO actual = resource.webClient()
                .path( "projects" )
                .path( expected.getId() )
                .type( type( ProjectDTO.class ) )
                .get();

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingFieldByField(
                        projectConverter.toDTO( expected ) );
    }

    @Test
    public void put() throws Exception {
        Project project = aProjectTestBuilder()
                .withName( "oldName" )
                .withDescription( "oldDescription" )
                .build();

        resource.save( project );

        ProjectCreateDTO expected = aProjectCreateDTOTestBuilder()
                .withName( "updatedName" )
                .withDescription( "updatedDescription" )
                .build();

        resource.webClient()
                .path( "projects" )
                .path( project.getId() )
                .put( expected );

        Project actual = resource.find( project.getId(), Project.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "name", "description" );
    }

    @Test
    public void delete() throws Exception {
        Project expected = aProjectTestBuilder().build();
        resource.save( expected );

        resource.webClient()
                .path( "projects" )
                .path( expected.getId() )
                .delete();

        Project actual = resource.find( expected.getId(), Project.class );

        assertThat( actual ).isNull();
    }
}