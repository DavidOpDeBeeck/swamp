package de.daxu.swamp.api.location;

import de.daxu.swamp.api.location.converter.LocationConverter;
import de.daxu.swamp.api.location.dto.LocationDTO;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.continent.ContinentTestBuilder.aContinentTestBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    private LocationConverter locationConverter = spring.getInstance( LocationConverter.class );

    @Test
    public void getAll() throws Exception {
        Continent continent = aContinentTestBuilder().build();
        resource.save( continent );

        List<LocationDTO> locations = resource.webClient()
                .path( "locations" )
                .type( list( LocationDTO.class ) )
                .get();

        assertThat( locations ).isNotEmpty();
        assertThat( locations )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        locationConverter.toDTO( continent ) );
    }
}