package de.daxu.swamp.api.version;

import de.daxu.swamp.api.version.dto.VersionDTO;
import de.daxu.swamp.test.overrides.PropertyOverrides;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.api.ProjectDTOTestConstants.VersionDTOs.VERSION;
import static de.daxu.swamp.api.ProjectDTOTestConstants.VersionDTOs.aVersionDTO;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class VersionResourceTest {

    @ClassRule
    public static SpringRule spring = spring(
            new PropertyOverrides.Builder()
                    .withOverride("application.version", VERSION)
                    .build());
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    @Test
    public void get() throws Exception {
        VersionDTO expected = aVersionDTO();

        VersionDTO actual = resource.webClient()
                .path("version")
                .type(VersionDTO.class)
                .get();

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

}