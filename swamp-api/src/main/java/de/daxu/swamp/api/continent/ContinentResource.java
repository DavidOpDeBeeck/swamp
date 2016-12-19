package de.daxu.swamp.api.continent;

import de.daxu.swamp.api.continent.converter.ContinentConverter;
import de.daxu.swamp.api.continent.converter.ContinentCreateConverter;
import de.daxu.swamp.api.continent.dto.ContinentCreateDTO;
import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.continent.ContinentResource.CONTINENTS_URL;

@RestController
@RequestMapping( CONTINENTS_URL )
public class ContinentResource {

    public static final String CONTINENTS_URL = "/continents";

    private final ResponseFactory response;
    private final LocationService locationService;
    private final ContinentConverter continentConverter;
    private final ContinentCreateConverter continentCreateConverter;

    @Autowired
    public ContinentResource( ResponseFactory responseFactory, LocationService locationService, ContinentConverter continentConverter, ContinentCreateConverter continentCreateConverter ) {
        this.response = responseFactory;
        this.locationService = locationService;
        this.continentConverter = continentConverter;
        this.continentCreateConverter = continentCreateConverter;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getAll() {

        List<ContinentDTO> continents = locationService
                .getAllContinents()
                .stream()
                .map( continentConverter::toDTO )
                .collect( Collectors.toList() );

        return response.success( continents );
    }

    @RequestMapping( method = RequestMethod.POST )
    public Response post( @RequestBody ContinentCreateDTO dto ) {

        Continent continent = continentCreateConverter.toDomain( dto );
        continent = locationService.createContinent( continent );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( continent.getId() ).toUri();

        return response.created( location );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        ContinentDTO continentDTO = continentConverter.toDTO( continent );

        return response.success( continentDTO );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.PUT )
    public Response put( @PathVariable( "continentId" ) String continentId,
                         @RequestBody ContinentCreateDTO continentCreateDTO ) {

        Continent targetContinent = locationService.getContinent( continentId );
        Continent srcContinent = continentCreateConverter.toDomain( continentCreateDTO );

        if( targetContinent == null )
            return response.notFound( "Continent was not found!" );

        BeanUtils.copyProperties( srcContinent, targetContinent );
        locationService.updateContinent( targetContinent );

        ContinentDTO continentDTO = continentConverter.toDTO( targetContinent );

        return response.success( continentDTO );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.DELETE )
    public Response delete( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        locationService.deleteContinent( continent );

        return response.success();
    }
}
