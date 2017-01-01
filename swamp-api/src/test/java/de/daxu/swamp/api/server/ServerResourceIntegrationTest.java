package de.daxu.swamp.api.server;

import de.daxu.swamp.api.server.converter.ServerConverter;
import de.daxu.swamp.api.server.dto.ServerCreateDTO;
import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static de.daxu.swamp.api.server.dto.ServerCreateDTOTestBuilder.aServerCreateDTO;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.common.web.WebClient.type;
import static de.daxu.swamp.core.continent.ContinentTestBuilder.aContinent;
import static de.daxu.swamp.core.datacenter.DatacenterTestBuilder.aDatacenter;
import static de.daxu.swamp.core.server.ServerBuilderTestBuilder.aServer;
import static de.daxu.swamp.core.server.ServerBuilderTestBuilder.anotherServer;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerResourceIntegrationTest {


    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    private Continent continent;
    private Datacenter datacenter;
    private ServerConverter serverConverter = spring.getInstance( ServerConverter.class );

    @Before
    public void setUp() throws Exception {
        continent = aContinent().build();
        datacenter = aDatacenter().build();
        resource.save( continent );
        resource.save( datacenter );
        continent.addDatacenter( datacenter );
        resource.save( continent );
    }

    private String datacenterPath() {
        return format( "%s/%s/%s/%s", "continents", continent.getId(), "datacenters", datacenter.getId() );
    }

    @Test
    public void getAll() throws Exception {
        Server server1 = aServer().build();
        Server server2 = anotherServer().build();

        addServer( server1 );
        addServer( server2 );

        List<ServerDTO> servers = resource.webClient()
                .path( datacenterPath() )
                .path( "servers" )
                .type( list( ServerDTO.class ) )
                .get();

        assertThat( servers ).isNotEmpty();
        assertThat( servers )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        serverConverter.toDTO( server1 ),
                        serverConverter.toDTO( server2 ) );
    }

    @Test
    public void post() throws Exception {
        ServerCreateDTO dto = aServerCreateDTO().build();

        String id = resource.webClient()
                .path( datacenterPath() )
                .path( "servers" )
                .post( dto );

        Server actual = resource.find( id, Server.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        dto, "name" );
    }

    @Test
    public void get() throws Exception {
        Server expected = aServer().build();
        addServer( expected );

        ServerDTO actual = resource.webClient()
                .path( datacenterPath() )
                .path( "servers" )
                .path( expected.getId() )
                .type( type( ServerDTO.class ) )
                .get();

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingFieldByField(
                        serverConverter.toDTO( expected ) );
    }

    @Test
    public void put() throws Exception {
        Server server = aServer()
                .withName( "oldName" )
                .build();

        addServer( server );

        ServerCreateDTO expected = aServerCreateDTO()
                .withName( "updatedName" )
                .build();

        resource.webClient()
                .path( datacenterPath() )
                .path( "servers" )
                .path( server.getId() )
                .put( expected );

        Server actual = resource.find( server.getId(), Server.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "name" );
    }

    @Test
    public void delete() throws Exception {
        Server expected = aServer().build();
        addServer( expected );

        resource.webClient()
                .path( datacenterPath() )
                .path( "servers" )
                .path( expected.getId() )
                .delete();

        Server actual = resource.find( expected.getId(), Server.class );

        assertThat( actual ).isNull();
    }

    private void addServer( Server server ) {
        resource.save( server );
        datacenter.addServer( server );
        resource.save( datacenter );
    }
}