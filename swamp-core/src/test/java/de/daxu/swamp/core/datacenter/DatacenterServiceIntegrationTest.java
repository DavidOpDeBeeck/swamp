package de.daxu.swamp.core.datacenter;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.*;
import static de.daxu.swamp.core.LocationTestConstants.Servers.aServer;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class DatacenterServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule(spring);

    private DatacenterService datacenterService
            = spring.getInstance(DatacenterService.class);

    @Test
    public void addServerToDatacenter() throws Exception {
        Datacenter datacenter = aDatacenter();
        Server server = aServer();
        integration.save(datacenter);

        datacenterService.addServerToDatacenter(datacenter.getId(), server);

        Datacenter actual = integration.find(datacenter.getId(), Datacenter.class);
        assertThat(actual.getServers())
                .usingElementComparator(byReflection())
                .contains(server);
    }

    @Test
    public void removeServerFromDatacenter() throws Exception {
        Server server = aServer();
        Datacenter datacenter = aDatacenter(server);
        integration.save(datacenter);

        datacenterService.removeServerFromDatacenter(datacenter.getId(), server);

        Datacenter actual = integration.find(datacenter.getId(), Datacenter.class);
        assertThat(actual.getServers()).isEmpty();
    }

    @Test
    public void updateDatacenter() throws Exception {
        Datacenter datacenter = aDatacenter();
        integration.save(datacenter);

        datacenter.setName(ANOTHER_DATACENTER_NAME);
        Datacenter actual = datacenterService.updateDatacenter(datacenter);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aDatacenterBuilder()
                        .withName(ANOTHER_DATACENTER_NAME)
                        .build());
    }

    @Test
    public void getDatacenter() throws Exception {
        Datacenter datacenter = aDatacenter();
        integration.save(datacenter);

        Datacenter actual = datacenterService.getDatacenter(datacenter.getId());

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(datacenter);
    }

}