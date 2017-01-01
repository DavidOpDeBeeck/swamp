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
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.common.web.WebClient.type;
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
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    private Continent continent;
    private DatacenterConverter datacenterConverter = spring.getInstance( DatacenterConverter.class );

    @Before
    public void setUp() throws Exception {
        continent = aContinent().build();
        resource.save( continent );
    }

    private String continentPath() {
        return format( "%s/%s", "continents", continent.getId() );
    }

    @Test
    public void getAll() throws Exception {
        Datacenter datacenter1 = aDatacenter().build();
        Datacenter datacenter2 = anotherDatacenter().build();

        addDatacenter( datacenter1 );
        addDatacenter( datacenter2 );

        List<DatacenterDTO> datacenters = resource.webClient()
                .path( continentPath() )
                .path( "datacenters" )
                .type( list( DatacenterDTO.class ) )
                .get();

        assertThat( datacenters ).isNotEmpty();
        assertThat( datacenters )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        datacenterConverter.toDTO( datacenter1 ),
                        datacenterConverter.toDTO( datacenter2 ) );
    }

    @Test
    public void post() throws Exception {
        DatacenterCreateDTO dto = aDatacenterCreateDTO().build();

        String id = resource.webClient()
                .path( continentPath() )
                .path( "datacenters" )
                .post( dto );

        Datacenter actual = resource.find( id, Datacenter.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        dto, "name" );
    }

    @Test
    public void get() throws Exception {
        Datacenter expected = aDatacenter().build();
        addDatacenter( expected );

        DatacenterDTO actual = resource.webClient()
                .path( continentPath() )
                .path( "datacenters" )
                .path( expected.getId() )
                .type( type( DatacenterDTO.class ) )
                .get();

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingFieldByField(
                        datacenterConverter.toDTO( expected ) );
    }

    @Test
    public void put() throws Exception {
        Datacenter datacenter = aDatacenter()
                .withName( "oldName" )
                .build();

        addDatacenter( datacenter );

        DatacenterCreateDTO expected = aDatacenterCreateDTO()
                .withName( "updatedName" )
                .build();

        resource.webClient()
                .path( continentPath() )
                .path( "datacenters" )
                .path( datacenter.getId() )
                .put( expected );

        Datacenter actual = resource.find( datacenter.getId(), Datacenter.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "name" );
    }

    @Test
    public void delete() throws Exception {
        Datacenter expected = aDatacenter().build();
        addDatacenter( expected );

        resource.webClient()
                .path( continentPath() )
                .path( "datacenters" )
                .path( expected.getId() )
                .delete();

        Datacenter actual = resource.find( expected.getId(), Datacenter.class );

        assertThat( actual ).isNull();
    }

    private void addDatacenter( Datacenter datacenter ) {
        resource.save( datacenter );
        continent.addDatacenter( datacenter );
        resource.save( continent );
    }
}