package de.daxu.swamp.api.server;

import de.daxu.swamp.api.server.converter.ServerConverter;
import de.daxu.swamp.api.server.dto.ServerCreateDTO;
import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.LocationDTOTestConstants.ServerDTOs.aServerCreateDTO;
import static de.daxu.swamp.api.LocationDTOTestConstants.ServerDTOs.anotherServerCreateDTO;
import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.core.LocationTestConstants.Continents.aContinent;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.aDatacenter;
import static de.daxu.swamp.core.LocationTestConstants.Servers.aServer;
import static de.daxu.swamp.core.LocationTestConstants.Servers.anotherServer;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule(spring);

    private ServerConverter serverConverter = spring.getInstance(ServerConverter.class);

    @Test
    public void getAll() throws Exception {
        Server aServer = aServer();
        Server anotherServer = anotherServer();
        Datacenter datacenter = aDatacenter(aServer, anotherServer);
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        List<ServerDTO> servers = resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .path("servers")
                .type(list(ServerDTO.class))
                .get();

        assertThat(servers)
                .usingElementComparator(byReflection())
                .contains(
                        serverConverter.toDTO(aServer),
                        serverConverter.toDTO(anotherServer));
    }

    @Test
    public void post() throws Exception {
        Datacenter datacenter = aDatacenter();
        Continent continent = aContinent(datacenter);
        resource.save(continent);
        ServerCreateDTO dto = aServerCreateDTO();

        String id = resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .path("servers")
                .post(dto);

        Server server = resource.find(id, Server.class);

        assertThat(server)
                .usingComparator(byReflection())
                .isEqualTo(aServer());
    }

    @Test
    public void get() throws Exception {
        Server server = aServer();
        Datacenter datacenter = aDatacenter(server);
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        ServerDTO actual = resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .path("servers")
                .path(server.getId())
                .type(ServerDTO.class)
                .get();

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(serverConverter.toDTO(server));
    }

    @Test
    public void put() throws Exception {
        Server server = aServer();
        Datacenter datacenter = aDatacenter(server);
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        ServerCreateDTO updated = anotherServerCreateDTO();

        resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .path("servers")
                .path(server.getId())
                .put(updated);

        Server actual = resource.find(server.getId(), Server.class);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(anotherServer());
    }

    @Test
    public void delete() throws Exception {
        Server server = aServer();
        Datacenter datacenter = aDatacenter(server);
        Continent continent = aContinent(datacenter);
        resource.save(continent);

        resource.webClient()
                .path("continents")
                .path(continent.getId())
                .path("datacenters")
                .path(datacenter.getId())
                .path("servers")
                .path(server.getId())
                .delete();

        Server actual = resource.find(server.getId(), Server.class);

        assertThat(actual).isNull();
    }
}