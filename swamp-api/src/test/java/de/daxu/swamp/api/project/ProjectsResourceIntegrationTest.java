package de.daxu.swamp.api.project;

import de.daxu.swamp.api.project.converter.ProjectConverter;
import de.daxu.swamp.api.project.dto.ProjectCreateDTO;
import de.daxu.swamp.api.project.dto.ProjectDTO;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.project.ProjectCommandService;
import de.daxu.swamp.test.overrides.BeanOverrides;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.ProjectDTOTestConstants.ProjectCreateDTOs.aProjectCreateDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.ProjectCreateDTOs.anotherProjectCreateDTO;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.aContainerTemplate;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.aProject;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.anotherProject;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring(
            new BeanOverrides.Builder()
                    .withOverride("projectCommandService", new ProjectCommandServiceStub())
                    .build());
    @Rule
    public final ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private ProjectConverter projectConverter;

    @Before
    public void setUp() throws Exception {
        projectConverter = spring.getInstance(ProjectConverter.class);
    }

    @Test
    public void getAll() throws Exception {
        Project aProject = aProject();
        Project anotherProject = anotherProject();
        resource.save(aProject, anotherProject);

        List<ProjectDTO> projects = resource.webClient()
                .path("projects")
                .type(list(ProjectDTO.class))
                .get();

        assertThat(projects)
                .usingElementComparator(byReflection())
                .contains(
                        projectConverter.toDTO(aProject),
                        projectConverter.toDTO(anotherProject));
    }

    @Test
    public void post() throws Exception {
        ProjectCreateDTO dto = aProjectCreateDTO();

        String id = resource.webClient()
                .path("projects")
                .post(dto);

        Project project = resource.find(id, Project.class);

        assertThat(project)
                .usingComparator(byReflection())
                .isEqualTo(aProject());
    }

    @Test
    public void get() throws Exception {
        Project expected = aProject();
        resource.save(expected);

        ProjectDTO actual = resource.webClient()
                .path("projects")
                .path(expected.getId())
                .type(ProjectDTO.class)
                .get();

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(projectConverter.toDTO(expected));
    }

    @Test
    public void put() throws Exception {
        Project project = aProject();
        resource.save(project);

        ProjectCreateDTO updated = anotherProjectCreateDTO();

        resource.webClient()
                .path("projects")
                .path(project.getId())
                .put(updated);

        Project actual = resource.find(project.getId(), Project.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(anotherProject());
    }

    @Test
    public void delete() throws Exception {
        Project project = aProject(aContainerTemplate());
        resource.save(project);

        resource.webClient()
                .path("projects")
                .path(project.getId())
                .delete();

        Project actual = resource.find(project.getId(), Project.class);

        assertThat(actual).isNull();
    }

    public static class ProjectCommandServiceStub extends ProjectCommandService {

        ProjectCommandServiceStub() {
            super(null, null);
        }

        @Override
        public void createProject(String id, String name, String description) {}
    }
}