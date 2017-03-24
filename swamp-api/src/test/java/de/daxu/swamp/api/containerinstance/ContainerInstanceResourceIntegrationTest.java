package de.daxu.swamp.api.containerinstance;

import de.daxu.swamp.scheduling.query.build.BuildView;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.query.log.LogView;
import de.daxu.swamp.scheduling.query.project.ProjectView;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.build.BuildResource.BUILD_URL;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.scheduling.ViewTestConstants.BuildViews.aBuildView;
import static de.daxu.swamp.scheduling.ViewTestConstants.ContainerInstanceViews.aContainerInstanceView;
import static de.daxu.swamp.scheduling.ViewTestConstants.ContainerInstanceViews.anotherContainerInstanceView;
import static de.daxu.swamp.scheduling.ViewTestConstants.LogViews.aLogView;
import static de.daxu.swamp.scheduling.ViewTestConstants.ProjectViews.aProjectView;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerInstanceResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    @Test
    public void getAll() throws Exception {
        ContainerInstanceView aContainerInstanceView = aContainerInstanceView();
        ContainerInstanceView anotherContainerInstanceView = anotherContainerInstanceView();
        BuildView buildView = aBuildView(aContainerInstanceView, anotherContainerInstanceView);
        ProjectView projectView = aProjectView(buildView);
        resource.save(projectView, aContainerInstanceView, anotherContainerInstanceView);

        List<ContainerInstanceView> views = resource.webClient()
                .path(BUILD_URL)
                .path(buildView.getBuildId().value())
                .path("container-instances")
                .type(list(ContainerInstanceView.class))
                .get();

        assertThat(views)
                .usingElementComparator(byReflection())
                .contains(aContainerInstanceView, anotherContainerInstanceView);
    }

    @Test
    public void get() throws Exception {
        ContainerInstanceView containerInstanceView = aContainerInstanceView();
        BuildView buildView = aBuildView(containerInstanceView);
        ProjectView projectView = aProjectView(buildView);
        resource.save(projectView, containerInstanceView);

        ContainerInstanceView view = resource.webClient()
                .path(BUILD_URL)
                .path(buildView.getBuildId().value())
                .path("container-instances")
                .path(containerInstanceView.getContainerInstanceId().value())
                .type(ContainerInstanceView.class)
                .get();

        assertThat(view)
                .usingComparator(byReflection())
                .isEqualTo(containerInstanceView);
    }

    @Test
    public void getLogs() throws Exception {
        LogView logView = aLogView();
        ContainerInstanceView containerInstanceView = aContainerInstanceView();
        BuildView buildView = aBuildView(containerInstanceView);
        ProjectView projectView = aProjectView(buildView);
        resource.save(projectView, containerInstanceView, logView);

        LogView view = resource.webClient()
                .path(BUILD_URL)
                .path(buildView.getBuildId().value())
                .path("container-instances")
                .path(containerInstanceView.getContainerInstanceId().value())
                .path("logs")
                .type(LogView.class)
                .get();

        assertThat(view)
                .usingComparator(byReflection())
                .isEqualTo(logView);
    }

}