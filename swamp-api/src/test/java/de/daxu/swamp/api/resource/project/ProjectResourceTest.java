package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.integration.ResourceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceTest extends ResourceIntegrationTest {

    @Test
    @Ignore
    @Transactional
    public void getAll() throws Exception {
        Project expected = aProjectTestBuilder().build();

        entityManager().persist( expected );
        entityManager().flush();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        Project actual = restTemplate().getForObject( "/projects/" + expected.getId(), Project.class );

        assertThat( expected )
                .usingDefaultComparator()
                .isEqualTo( actual );
    }

    @Test
    public void post() throws Exception {

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