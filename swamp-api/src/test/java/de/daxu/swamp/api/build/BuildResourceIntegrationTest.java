package de.daxu.swamp.api.build;

import de.daxu.swamp.scheduling.query.build.BuildView;
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
import static de.daxu.swamp.scheduling.ViewTestConstants.ProjectViews.aProjectView;
import static de.daxu.swamp.scheduling.ViewTestConstants.ProjectViews.anotherProjectView;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class BuildResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    @Test
    public void getAll() throws Exception {
        ProjectView aProjectView = aProjectView();
        ProjectView anotherProjectView = anotherProjectView();
        resource.save(aProjectView, anotherProjectView);

        List<ProjectView> views = resource.webClient()
                .path(BUILD_URL)
                .type(list(ProjectView.class))
                .get();

        assertThat(views)
                .usingElementComparator(byReflection())
                .contains(aProjectView, anotherProjectView);
    }

    @Test
    public void get() throws Exception {
        BuildView buildView = aBuildView();
        ProjectView projectView = aProjectView(buildView);
        resource.save(projectView);

        BuildView actual = resource.webClient()
                .path(BUILD_URL)
                .path(buildView.getBuildId().value())
                .type(BuildView.class)
                .get();

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(buildView);
    }

}