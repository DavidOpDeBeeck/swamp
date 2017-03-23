package de.daxu.swamp.api.datacenter;

import de.daxu.swamp.api.datacenter.converter.DatacenterConverter;
import de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTO;
import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.LocationDTOTestConstants.DatacenterDTOs.aDatacenterCreateDTO;
import static de.daxu.swamp.api.LocationDTOTestConstants.DatacenterDTOs.anotherDatacenterCreateDTO;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.LocationTestConstants.Continents.aContinent;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.aDatacenter;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.anotherDatacenter;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class DatacenterResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private DatacenterConverter datacenterConverter = spring.getInstance(DatacenterConverter.class);

    @Test
    public void getAll() throws Exception {
        Datacenter aDatacenter = aDatacenter();
        Datacenter anotherDatacenter = anotherDatacenter();
        Continent continent = aContinent(aDatacenter, anotherDatacenter);
        resource.save(continent);

        List<DatacenterDTO> datacenters = resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .type(list(DatacenterDTO.class))
                .get();

        assertThat(datacenters)
                .usingElementComparator(byReflection())
                .contains(datacenterConverter.toDTO(aDatacenter), datacenterConverter.toDTO(anotherDatacenter));
    }

    @Test
    public void post() throws Exception {
        Continent continent = aContinent();
        resource.save(continent);
        DatacenterCreateDTO dto = aDatacenterCreateDTO();

        String id = resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .post(dto);

        Datacenter actual = resource.find(id, Datacenter.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aDatacenter());
    }

    @Test
    public void get() throws Exception {
        Datacenter datacenter = aDatacenter();
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        DatacenterDTO actual = resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .type(DatacenterDTO.class)
                .get();

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(datacenterConverter.toDTO(datacenter));
    }

    @Test
    public void put() throws Exception {
        Datacenter datacenter = aDatacenter();
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        DatacenterCreateDTO updated = anotherDatacenterCreateDTO();

        resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .put(updated);

        Datacenter actual = resource.find(datacenter.getId(), Datacenter.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(anotherDatacenter());
    }

    @Test
    public void delete() throws Exception {
        Datacenter datacenter = aDatacenter();
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .delete();

        Datacenter actual = resource.find(datacenter.getId(), Datacenter.class);

        assertThat(actual).isNull();
    }
}