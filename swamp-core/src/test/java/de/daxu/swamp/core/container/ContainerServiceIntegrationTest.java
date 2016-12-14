package de.daxu.swamp.core.container;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.HibernateRule;
import de.daxu.swamp.test.SpringRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;

public class ContainerServiceIntegrationTest {

    @Rule
    public SpringRule spring = new SpringRule();

    @Rule
    public HibernateRule database = new HibernateRule( spring );

    @Test
    public void addContainerToProject() throws Exception {
        Project project = aProjectTestBuilder().build();
        database.entityManager().persist( project );

        database.entityManager().find( Project.class, project.getId() );

       /* RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<Project> projectFromAPI = template.getForEntity( "http://localhost:8081/projects", Project.class );

        assertThat( projectFromAPI ).isNotNull();*/
    }
}