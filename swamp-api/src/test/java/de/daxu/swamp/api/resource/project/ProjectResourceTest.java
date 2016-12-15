package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.IntegrationTestRule;
import de.daxu.swamp.test.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static de.daxu.swamp.test.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceTest {

    @ClassRule
    public static SpringRule spring = spring();

    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule( spring );

    @Test
    public void getAll() throws Exception {
        Project project = aProjectTestBuilder().build();
        integration.persist( project );

        integration.entityManager().find( Project.class, project.getId() );
        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> projectFromAPI = template.getForEntity( "http://localhost:8081/projects", String.class );

        assertThat( projectFromAPI ).isNotNull();
    }

    @Test
    public void post() throws Exception {
        Project expected = aProjectTestBuilder().build();

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<Response> httpResponse = template.postForEntity( "http://localhost:8081/projects", expected, Response.class, new Object() );
        Response response = httpResponse.getBody();

        String location = response.getMeta().getLocation();
        String id = location.substring( location.lastIndexOf( '/' ) + 1, location.length() );

        ResponseEntity<Response> projectFromAPI = template.getForEntity( "http://localhost:8081/projects/" + id, Response.class );
        Project actual = ( Project ) projectFromAPI.getBody().getData();

        assertThat( expected.getName() ).isEqualTo( actual.getName() );
        assertThat( expected.getDescription() ).isEqualTo( actual.getDescription() );
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