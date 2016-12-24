package de.daxu.swamp.api.continent;

import de.daxu.swamp.api.continent.converter.ContinentConverter;
import de.daxu.swamp.api.continent.dto.ContinentCreateDTO;
import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.test.rule.ResourceIntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static de.daxu.swamp.api.continent.dto.ContinentCreateDTOTestBuilder.aContinentCreateDTOTestBuilder;
import static de.daxu.swamp.common.web.WebClient.list;
import static de.daxu.swamp.common.web.WebClient.type;
import static de.daxu.swamp.core.continent.ContinentTestBuilder.aContinentTestBuilder;
import static de.daxu.swamp.core.continent.ContinentTestBuilder.anotherContinentTestBuilder;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ContinentResourceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public ResourceIntegrationTestRule resource = new ResourceIntegrationTestRule( spring );

    private ContinentConverter continentConverter;

    @Before
    public void setUp() throws Exception {
        continentConverter = spring.getInstance( ContinentConverter.class );
    }

    @Test
    public void getAll() throws Exception {
        Continent continent1 = aContinentTestBuilder().build();
        Continent continent2 = anotherContinentTestBuilder().build();
        saveContinent( continent1, continent2 );

        List<ContinentDTO> continents = resource.webClient()
                .path( "continents" )
                .type( list( ContinentDTO.class ) )
                .get();

        assertThat( continents ).isNotEmpty();
        assertThat( continents )
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        continentConverter.toDTO( continent1 ),
                        continentConverter.toDTO( continent2 ) );
    }

    @Test
    public void post() throws Exception {
        ContinentCreateDTO dto = aContinentCreateDTOTestBuilder().build();

        String id = resource.webClient()
                .path( "continents" )
                .post( dto );

        Continent actual = resource.find( id, Continent.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        dto, "name" );
    }

    @Test
    public void get() throws Exception {
        Continent expected = aContinentTestBuilder().build();
        saveContinent( expected );

        ContinentDTO actual = resource.webClient()
                .path( "continents" )
                .path( expected.getId() )
                .type( type( ContinentDTO.class ) )
                .get();

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingFieldByField(
                        continentConverter.toDTO( expected ) );
    }

    @Test
    public void put() throws Exception {
        Continent continent = aContinentTestBuilder()
                .withName( "oldName" )
                .build();

        saveContinent( continent );

        ContinentCreateDTO expected = aContinentCreateDTOTestBuilder()
                .withName( "updatedName" )
                .build();

        resource.webClient()
                .path( "continents" )
                .path( continent.getId() )
                .put( expected );

        Continent actual = resource.find( continent.getId(), Continent.class );

        assertThat( actual ).isNotNull();
        assertThat( actual )
                .isEqualToComparingOnlyGivenFields(
                        expected, "name" );
    }

    @Test
    public void delete() throws Exception {
        Continent expected = aContinentTestBuilder().build();
        saveContinent( expected );

        resource.webClient()
                .path( "continents" )
                .path( expected.getId() )
                .delete();

        Continent actual = resource.find( expected.getId(), Continent.class );

        assertThat( actual ).isNull();
    }

    private void saveContinent( Continent... continents ) {
        Arrays.stream( continents )
                .map( Continent::getDatacenters )
                .flatMap( Set::stream )
                .forEach( this::saveDatacenter );
        Arrays.stream( continents )
                .forEach( resource::save );
    }

    private void saveDatacenter( Datacenter... datacenters ) {
        Arrays.stream( datacenters )
                .map( Datacenter::getServers )
                .flatMap( Set::stream )
                .forEach( resource::save );
        Arrays.stream( datacenters )
                .forEach( resource::save );
    }
}