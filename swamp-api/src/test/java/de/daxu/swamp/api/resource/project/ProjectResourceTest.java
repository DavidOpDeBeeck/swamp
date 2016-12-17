package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.ResourceIntegrationTestRule;
import de.daxu.swamp.test.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static de.daxu.swamp.test.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceTest {

    @ClassRule
    public static SpringRule spring = spring();

    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    @Test
    public void getAll() throws Exception {
        Project project = aProjectTestBuilder().build();
        resource.persist( project );

        List<ProjectDTO> projects = resource.getList( "/projects", ProjectDTO.class );

        assertThat( projects ).isNotEmpty();
    }

    @Test
    public void post() throws Exception {
       /* Project expected = aProjectTestBuilder().build();

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<Response> httpResponse = template.postForEntity( "http://localhost:8888/projects", expected, Response.class, new Object() );
        Response response = httpResponse.getBody();

        String location = response.getMeta().getLocation();
        String id = location.substring( location.lastIndexOf( '/' ) + 1, location.length() );

        ResponseEntity<Response> projectFromAPI = template.getForEntity( "http://localhost:8888/projects/" + id, Response.class );
        Project actual = ( Project ) projectFromAPI.getBody().getData();

        assertThat( expected.getName() ).isEqualTo( actual.getName() );
        assertThat( expected.getDescription() ).isEqualTo( actual.getDescription() );*/
    }

//    @Test
//    public void get() throws Exception {
//
//    }
//
//    @Test
//    public void put() throws Exception {
//
//    }
//
//    @Test
//    public void delete() throws Exception {
//
//    }

}