package de.daxu.swamp.api.version;

import de.daxu.swamp.api.version.dto.VersionDTO;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.api.version.dto.VersionDTOTestBuilder.aVersionDTO;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class VersionResourceTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private String version;

    @Before
    public void setUp() throws Exception {
        version = spring.getProperty("application.version");
    }

    @Test
    public void get() throws Exception {
        VersionDTO expected = aVersionDTO().withVersion(version).build();

        VersionDTO actual = resource.webClient()
                .path("version")
                .type(VersionDTO.class)
                .get();

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

}