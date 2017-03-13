package de.daxu.swamp.api.container;

import de.daxu.swamp.api.container.converter.ContainerConverter;
import de.daxu.swamp.api.container.dto.ContainerCreateDTO;
import de.daxu.swamp.api.container.dto.ContainerDTO;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.container.dto.ContainerCreateDTOTestBuilder.aContainerCreateDTO;
import static de.daxu.swamp.api.container.dto.ContainerCreateDTOTestBuilder.anotherContainerCreateDTO;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.container.ContainerTestBuilder.aContainer;
import static de.daxu.swamp.core.container.ContainerTestBuilder.anotherContainer;
import static de.daxu.swamp.core.project.ProjectTestBuilder.aProject;
import static de.daxu.swamp.test.comparator.TestComparators.ignoreId;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class ContainerResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private Project project;
    private ContainerConverter containerConverter = spring.getInstance(ContainerConverter.class);

    @Before
    public void setUp() throws Exception {
        project = aProject().build();
        resource.save(project);
    }

    private String projectPath() {
        return format("%s/%s", "projects", project.getId());
    }

    @Test
    public void getAll() throws Exception {
        Container aContainer = aContainer().build();
        Container anotherContainer = anotherContainer().build();

        addContainer(aContainer);
        addContainer(anotherContainer);

        List<ContainerDTO> containers = resource.webClient()
                .path(projectPath())
                .path("containers")
                .type(list(ContainerDTO.class))
                .get();

        assertThat(containers)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        containerConverter.toDTO(aContainer),
                        containerConverter.toDTO(anotherContainer));
    }

    @Test
    public void post() throws Exception {
        ContainerCreateDTO container = aContainerCreateDTO().build();

        String id = resource.webClient()
                .path(projectPath())
                .path("containers")
                .post(container);

        Container actual = resource.find(id, Container.class);
        Container expected = aContainer().build();

        assertThat(actual.getAliases())
                .hasSameElementsAs(expected.getAliases());
        assertThat(actual.getRunConfiguration())
                .usingComparator(ignoreId())
                .isEqualTo(expected.getRunConfiguration());
        assertThat(actual.getPortMappings())
                .usingElementComparator(ignoreId())
                .hasSameElementsAs(expected.getPortMappings());
        assertThat(actual.getEnvironmentVariables())
                .usingElementComparator(ignoreId())
                .hasSameElementsAs(expected.getEnvironmentVariables());
    }

    @Test
    public void get() throws Exception {
        Container expected = aContainer().build();
        addContainer(expected);

        ContainerDTO actual = resource.webClient()
                .path(projectPath())
                .path("containers")
                .path(expected.getId())
                .type(ContainerDTO.class)
                .get();

        assertReflectionEquals(actual, containerConverter.toDTO(expected));
    }

    @Test
    public void put() throws Exception {
        Container container = aContainer().build();
        addContainer(container);

        ContainerCreateDTO updated = anotherContainerCreateDTO().build();

        resource.webClient()
                .path(projectPath())
                .path("containers")
                .path(container.getId())
                .put(updated);

        Container actual = resource.find(container.getId(), Container.class);
        Container expected = anotherContainer().build();

        assertThat(actual.getAliases())
                .hasSameElementsAs(expected.getAliases());
        assertThat(actual.getRunConfiguration())
                .usingComparator(ignoreId())
                .isEqualTo(expected.getRunConfiguration());
        assertThat(actual.getPortMappings())
                .usingElementComparator(ignoreId())
                .hasSameElementsAs(expected.getPortMappings());
        assertThat(actual.getEnvironmentVariables())
                .usingElementComparator(ignoreId())
                .hasSameElementsAs(expected.getEnvironmentVariables());
    }

    @Test
    public void delete() throws Exception {
        Container expected = aContainer().build();
        addContainer(expected);

        resource.webClient()
                .path(projectPath())
                .path("containers")
                .path(expected.getId())
                .delete();

        Container actual = resource.find(expected.getId(), Container.class);

        assertThat(actual).isNull();
    }

    private void addContainer(Container container) {
        resource.save(container);
        project.addContainer(container);
        resource.save(project);
    }
}