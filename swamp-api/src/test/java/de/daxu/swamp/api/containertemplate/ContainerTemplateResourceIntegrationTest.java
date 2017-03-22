package de.daxu.swamp.api.containertemplate;

import de.daxu.swamp.api.containertemplate.converter.ContainerTemplateConverter;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateCreateDTO;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateDTO;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.api.ProjectDTOTestConstants.ContainerTemplateCreateDTOs.aContainerTemplateCreateDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.ContainerTemplateCreateDTOs.anotherContainerCreateDTO;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.aContainerTemplate;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.anotherContainerTemplate;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.aProject;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.aProjectBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerTemplateResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private ContainerTemplateConverter containerTemplateConverter = spring.getInstance(ContainerTemplateConverter.class);

    @Test
    public void getAll() throws Exception {
        ContainerTemplate aContainerTemplate = aContainerTemplate();
        ContainerTemplate anotherContainerTemplate = anotherContainerTemplate();
        Project project = aProjectBuilder()
                .withContainers(newHashSet(aContainerTemplate, anotherContainerTemplate))
                .build();
        resource.save(project);

        List<ContainerTemplateDTO> containers = resource.webClient()
                .path("projects")
                .path(project.getId())
                .path("container-templates")
                .type(list(ContainerTemplateDTO.class))
                .get();

        assertThat(containers)
                .usingElementComparator(byReflection())
                .contains(
                        containerTemplateConverter.toDTO(aContainerTemplate),
                        containerTemplateConverter.toDTO(anotherContainerTemplate));
    }

    @Test
    public void post() throws Exception {
        Project project = aProject(aContainerTemplate());
        resource.save(project);
        ContainerTemplateCreateDTO containerTemplate = aContainerTemplateCreateDTO();

        String id = resource.webClient()
                .path("projects")
                .path(project.getId())
                .path("container-templates")
                .post(containerTemplate);

        ContainerTemplate actual = resource.find(id, ContainerTemplate.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aContainerTemplate());
    }

    @Test
    public void get() throws Exception {
        ContainerTemplate containerTemplate = aContainerTemplate();
        Project project = aProjectBuilder()
                .withContainers(newHashSet(containerTemplate))
                .build();
        resource.save(project);

        ContainerTemplateDTO actual = resource.webClient()
                .path("projects")
                .path(project.getId())
                .path("container-templates")
                .path(containerTemplate.getId())
                .type(ContainerTemplateDTO.class)
                .get();

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(containerTemplateConverter.toDTO(aContainerTemplate()));
    }

    @Test
    public void put() throws Exception {
        ContainerTemplate containerTemplate = aContainerTemplate();
        Project project = aProjectBuilder()
                .withContainers(newHashSet(containerTemplate))
                .build();
        resource.save(project);

        ContainerTemplateCreateDTO updated = anotherContainerCreateDTO();

        resource.webClient()
                .path("projects")
                .path(project.getId())
                .path("container-templates")
                .path(containerTemplate.getId())
                .put(updated);

        ContainerTemplate actual = resource.find(containerTemplate.getId(), ContainerTemplate.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(anotherContainerTemplate());
    }

    @Test
    public void delete() throws Exception {
        ContainerTemplate containerTemplate = aContainerTemplate();
        Project project = aProjectBuilder()
                .withContainers(newHashSet(containerTemplate))
                .build();
        resource.save(project);

        resource.webClient()
                .path("projects")
                .path(project.getId())
                .path("container-templates")
                .path(containerTemplate.getId())
                .delete();

        ContainerTemplate actual = resource.find(containerTemplate.getId(), ContainerTemplate.class);

        assertThat(actual).isNull();
    }

}