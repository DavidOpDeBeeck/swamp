package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectRepository;
import de.daxu.swamp.test.integration.ResourceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;

public class ProjectResourceTest extends ResourceIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void getAll() throws Exception {
        Project expected = aProjectTestBuilder().build();

        expected = projectRepository.save( expected );

        Project actual = restTemplate().getForObject( "/projects/" + expected.getId(), Project.class );

//        assertThat( actual )
//                .usingDefaultComparator()
//                .isEqualTo( expected );
    }

    @Test
    public void post() throws Exception {
        List<Project> all = projectRepository.findAll();
    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void put() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}