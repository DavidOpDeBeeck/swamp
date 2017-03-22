package de.daxu.swamp.core.continent;

import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.core.LocationTestConstants.Continents.*;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.aDatacenter;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContinentServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule(spring);

    private ContinentService continentService
            = spring.getInstance(ContinentService.class);

    @Test
    public void createContinent() throws Exception {
        Continent continent = aContinent();

        continentService.createContinent(continent);

        Continent actual = integration.find(continent.getId(), Continent.class);
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(continent);
    }

    @Test
    public void updateContinent() throws Exception {
        Continent continent = aContinent();
        integration.save(continent);

        continent.setName(ANOTHER_CONTINENT_NAME);
        continentService.updateContinent(continent);

        Continent actual = integration.find(continent.getId(), Continent.class);
        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aContinentBuilder()
                        .withName(ANOTHER_CONTINENT_NAME).build());
    }

    @Test
    public void deleteContinent() throws Exception {
        Continent continent = aContinent();
        integration.save(continent);

        continentService.deleteContinent(continent);

        Continent actual = integration.find(continent.getId(), Continent.class);
        assertThat(actual).isNull();
    }

    @Test
    public void addDatacenterToContinent() throws Exception {
        Continent continent = aContinent();
        Datacenter datacenter = aDatacenter();
        integration.save(continent);

        continentService.addDatacenterToContinent(continent.getId(), datacenter);

        Continent actual = integration.find(continent.getId(), Continent.class);
        assertThat(actual.getDatacenters())
                .usingElementComparator(byReflection())
                .contains(datacenter);
    }

    @Test
    public void removeDatacenterFromContinent() throws Exception {
        Datacenter datacenter = aDatacenter();
        Continent continent = aContinent(datacenter);
        integration.save(continent);

        continentService.removeDatacenterFromContinent(continent.getId(), datacenter);

        Continent actual = integration.find(continent.getId(), Continent.class);
        assertThat(actual.getDatacenters())
                .isEmpty();
    }

    @Test
    public void getContinent() throws Exception {
        Continent continent = aContinent();
        integration.save(continent);

        Continent actual = continentService.getContinent(continent.getId());

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(continent);
    }

    @Test
    public void getAllContinents() throws Exception {
        Continent aContinent = aContinent();
        Continent anotherContinent = anotherContinent();
        integration.save(aContinent, anotherContinent);

        Collection<Continent> actual = continentService.getAllContinents();

        assertThat(actual)
                .usingElementComparator(byReflection())
                .contains(aContinent, anotherContinent);
    }

}