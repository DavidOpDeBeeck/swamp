package de.daxu.swamp.core.containertemplate;

import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.Aliases.ANOTHER_ALIAS;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.EnvironmentVariables.anotherEnvironmentVariable;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.PortMappings.anotherPortMapping;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.RunConfigurations.anotherImageConfiguration;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.aContainerTemplate;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.aContainerTemplateBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerTemplateServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule(spring);

    private ContainerTemplateService containerTemplateService
            = spring.getInstance(ContainerTemplateService.class);

    @Test
    public void updateContainerTemplate() throws Exception {
        ContainerTemplate containerTemplate = aContainerTemplate();
        integration.save(containerTemplate);

        containerTemplate.setAliases(newHashSet(ANOTHER_ALIAS));
        containerTemplate.setRunConfiguration(anotherImageConfiguration());
        containerTemplate.setPortMappings(newHashSet(anotherPortMapping()));
        containerTemplate.setEnvironmentVariables(newHashSet(anotherEnvironmentVariable()));

        containerTemplateService.updateContainerTemplate(containerTemplate);
        ContainerTemplate actual = integration.find(containerTemplate.getId(), ContainerTemplate.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aContainerTemplateBuilder()
                        .withAliases(newHashSet(ANOTHER_ALIAS))
                        .withRunConfiguration(anotherImageConfiguration())
                        .withPortMappings(newHashSet(anotherPortMapping()))
                        .withEnvironmentVariables(newHashSet(anotherEnvironmentVariable()))
                        .build());
    }

    @Test
    public void findContainerTemplate() throws Exception {
        ContainerTemplate expected = aContainerTemplate();
        integration.save(expected);

        containerTemplateService.findContainerTemplate(expected.getId());

        ContainerTemplate actual = containerTemplateService.findContainerTemplate(expected.getId());
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aContainerTemplate());
    }
}