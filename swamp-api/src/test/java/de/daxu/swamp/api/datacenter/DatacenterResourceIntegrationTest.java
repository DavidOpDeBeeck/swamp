package de.daxu.swamp.api.datacenter;

import de.daxu.swamp.api.datacenter.converter.DatacenterConverter;
import de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTO;
import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTOTestBuilder.aDatacenterCreateDTO;
import static de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTOTestBuilder.anotherDatacenterCreateDTO;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.continent.ContinentTestBuilder.aContinent;
import static de.daxu.swamp.core.datacenter.DatacenterTestBuilder.aDatacenter;
import static de.daxu.swamp.core.datacenter.DatacenterTestBuilder.anotherDatacenter;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class DatacenterResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private DatacenterConverter datacenterConverter = spring.getInstance(DatacenterConverter.class);

    private Continent continent;

    @Before
    public void setUp() throws Exception {
        continent = aContinent().build();
        resource.save(continent);
    }

    private String continentPath() {
        return format("%s/%s", "continents", continent.getId());
    }

    @Test
    public void getAll() throws Exception {
        Datacenter aDatacenter = aDatacenter().build();
        Datacenter anotherDatacenter = anotherDatacenter().build();

        addDatacenter(aDatacenter);
        addDatacenter(anotherDatacenter);

        List<DatacenterDTO> datacenters = resource.webClient()
                .path(continentPath())
                .path("datacenters")
                .type(list(DatacenterDTO.class))
                .get();

        assertThat(datacenters)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        datacenterConverter.toDTO(aDatacenter),
                        datacenterConverter.toDTO(anotherDatacenter));
    }

    @Test
    public void post() throws Exception {
        DatacenterCreateDTO dto = aDatacenterCreateDTO().build();

        String id = resource.webClient()
                .path(continentPath())
                .path("datacenters")
                .post(dto);

        Datacenter actual = resource.find(id, Datacenter.class);

        assertThat(actual)
                .isEqualToIgnoringGivenFields(aDatacenter().build(), "id");
    }

    @Test
    public void get() throws Exception {
        Datacenter expected = aDatacenter().build();
        addDatacenter(expected);

        DatacenterDTO actual = resource.webClient()
                .path(continentPath())
                .path("datacenters")
                .path(expected.getId())
                .type(DatacenterDTO.class)
                .get();

        assertThat(actual)
                .isEqualToComparingFieldByField(
                        datacenterConverter.toDTO(expected));
    }

    @Test
    public void put() throws Exception {
        Datacenter datacenter = aDatacenter().build();
        addDatacenter(datacenter);

        DatacenterCreateDTO updated = anotherDatacenterCreateDTO().build();

        resource.webClient()
                .path(continentPath())
                .path("datacenters")
                .path(datacenter.getId())
                .put(updated);

        Datacenter actual = resource.find(datacenter.getId(), Datacenter.class);

        assertThat(actual)
                .isEqualToIgnoringGivenFields(anotherDatacenter().build(), "id");
    }

    @Test
    public void delete() throws Exception {
        Datacenter expected = aDatacenter().build();
        addDatacenter(expected);

        resource.webClient()
                .path(continentPath())
                .path("datacenters")
                .path(expected.getId())
                .delete();

        Datacenter actual = resource.find(expected.getId(), Datacenter.class);

        assertThat(actual).isNull();
    }

    private void addDatacenter(Datacenter datacenter) {
        resource.save(datacenter);
        continent.addDatacenter(datacenter);
        resource.save(continent);
    }
}