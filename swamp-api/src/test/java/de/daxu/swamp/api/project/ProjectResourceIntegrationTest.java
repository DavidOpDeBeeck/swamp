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

import static de.daxu.swamp.api.project.dto.ProjectCreateDTOTestBuilder.aProjectCreateDTO;
import static de.daxu.swamp.api.project.dto.ProjectCreateDTOTestBuilder.anotherProjectCreateDTO;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProject;
import static de.daxu.swamp.core.project.ProjectTestBuilder.anotherProject;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public final ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private ProjectConverter projectConverter;

    @Before
    public void setUp() throws Exception {
        projectConverter = spring.getInstance(ProjectConverter.class);
    }

    @Test
    public void getAll() throws Exception {
        Project aProject = aProject().build();
        Project anotherProject = anotherProject().build();
        resource.save(aProject, anotherProject);

        List<ProjectDTO> projects = resource.webClient()
                .path("projects")
                .type(list(ProjectDTO.class))
                .get();

        assertThat(projects)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        projectConverter.toDTO(aProject),
                        projectConverter.toDTO(anotherProject));
    }

    @Test
    public void post() throws Exception {
        ProjectCreateDTO dto = aProjectCreateDTO().build();

        String id = resource.webClient()
                .path("projects")
                .post(dto);

        Project project = resource.find(id, Project.class);

        assertThat(project)
                .isEqualToIgnoringGivenFields(aProject().build(), "id");
    }

    @Test
    public void get() throws Exception {
        Project expected = aProject().build();
        resource.save(expected);

        ProjectDTO actual = resource.webClient()
                .path("projects")
                .path(expected.getId())
                .type(ProjectDTO.class)
                .get();

        assertThat(actual)
                .isEqualToComparingFieldByField(
                        projectConverter.toDTO(expected));
    }

    @Test
    public void put() throws Exception {
        Project project = aProject().build();
        resource.save(project);

        ProjectCreateDTO updated = anotherProjectCreateDTO().build();

        resource.webClient()
                .path("projects")
                .path(project.getId())
                .put(updated);

        Project actual = resource.find(project.getId(), Project.class);

        assertThat(actual)
                .isEqualToIgnoringGivenFields(anotherProject().build(), "id");
    }

    @Test
    public void delete() throws Exception {
        Project project = aProject().build();
        resource.save(project);

        resource.webClient()
                .path("projects")
                .path(project.getId())
                .delete();

        Project actual = resource.find(project.getId(), Project.class);

        assertThat(actual).isNull();
    }
}