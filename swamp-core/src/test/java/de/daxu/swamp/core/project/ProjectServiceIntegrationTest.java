package de.daxu.swamp.core.project;

import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.aContainerTemplate;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.*;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule(spring);

    private ProjectService projectService = spring.getInstance(ProjectService.class);

    @Test
    public void createProject() throws Exception {
        Project expected = aProject();

        projectService.createProject(expected);

        Project actual = integration.find(expected.getId(), Project.class);
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(expected);
    }

    @Test
    public void updateProject() throws Exception {
        Project project = aProject();
        integration.save(project);

        project.setName(ANOTHER_PROJECT_NAME);
        project.setDescription(ANOTHER_PROJECT_DESCRIPTION);

        projectService.updateProject(project);

        Project actual = integration.find(project.getId(), Project.class);
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aProjectBuilder()
                        .withName(ANOTHER_PROJECT_NAME)
                        .withDescription(ANOTHER_PROJECT_DESCRIPTION)
                        .build());
    }

    @Test
    public void deleteProject() throws Exception {
        Project project = aProject(aContainerTemplate());
        integration.save(project);

        projectService.deleteProject(project);

        Project actual = integration.find(project.getId(), Project.class);
        assertThat(actual).isNull();
    }

    @Test
    public void getProject() throws Exception {
        Project project = aProject(aContainerTemplate());
        integration.save(project);

        Project actual = integration.find(project.getId(), Project.class);
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aProject(aContainerTemplate()));
    }

    @Test
    public void getAllProjects() throws Exception {
        Project aProject = aProject(aContainerTemplate());
        Project anotherProject = anotherProject();
        integration.save(aProject, anotherProject);

        Collection<Project> projects = projectService.getAllProjects();

        assertThat(projects)
                .usingElementComparator(byReflection())
                .containsExactlyInAnyOrder(aProject, anotherProject);
    }

    @Test
    public void addContainerTemplateToProject() throws Exception {
        Project project = aProjectBuilder()
                .withContainers(newHashSet()).build();
        integration.save(project);

        projectService.addContainerTemplateToProject(project.getId(), aContainerTemplate());

        Project actual = integration.find(project.getId(), Project.class);
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aProjectBuilder()
                        .withContainers(newHashSet(aContainerTemplate()))
                        .build());
    }

    @Test
    public void removeContainerTemplateToProject() throws Exception {
        ContainerTemplate containerTemplate = aContainerTemplate();
        Project project = aProjectBuilder()
                .withContainers(newHashSet(containerTemplate)).build();
        integration.save(project);

        projectService.removeContainerTemplateToProject(project.getId(), containerTemplate);

        Project actual = integration.find(project.getId(), Project.class);
        assertThat(actual.getContainerTemplates()).isEmpty();
    }
}