package de.daxu.swamp.core.container;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectRepository;
import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.core.project.ProjectTestBuilder.aProjectTestBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();

    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule( spring );

    @Test
    public void addContainerToProject() throws Exception {
        ProjectRepository repository = spring.getInstance( ProjectRepository.class );

        Project expected = aProjectTestBuilder().build();
        expected = repository.save( expected );

        Project actual = integration.find( expected.getId(), Project.class );

        assertThat( expected )
                .usingDefaultComparator()
                .isEqualTo( actual );
    }
}