package de.daxu.swamp.api.resource.location;

import de.daxu.swamp.api.converter.location.ContinentConverter;
import de.daxu.swamp.api.converter.location.ContinentCreateConverter;
import de.daxu.swamp.api.dto.location.ContinentCreateDTO;
import de.daxu.swamp.api.dto.location.ContinentDTO;
import de.daxu.swamp.common.response.Meta;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.location.ContinentResource.CONTINENTS_URL;
import static de.daxu.swamp.common.response.Response.Builder.aResponse;

@RestController
@RequestMapping( CONTINENTS_URL )
public class ContinentResource {

    public static final String CONTINENTS_URL = "/continents";

    @Autowired
    LocationService locationService;

    @Autowired
    ContinentConverter continentConverter;

    @Autowired
    ContinentCreateConverter continentCreateConverter;


    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> getAll() {

        List<ContinentDTO> continents = locationService
                .getAllContinents()
                .stream()
                .map( continentConverter::toDTO )
                .collect( Collectors.toList() );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( continents )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @RequestBody ContinentCreateDTO dto ) {

        Continent continent = continentCreateConverter.toDomain( dto );
        continent = locationService.createContinent( continent );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( continent )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.GET )
    public ResponseEntity<Response> get( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( Response.notFound( "Continent was not found!" ), HttpStatus.OK );

        ContinentDTO continentDTO = continentConverter.toDTO( continent );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( continentDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.PUT )
    public ResponseEntity<Response> put( @PathVariable( "continentId" ) String continentId,
                                         @RequestBody ContinentCreateDTO continentCreateDTO ) {

        Continent targetContinent = locationService.getContinent( continentId );
        Continent srcContinent = continentCreateConverter.toDomain( continentCreateDTO );

        if ( targetContinent == null )
            return new ResponseEntity<>( Response.notFound( "Continent was not found!" ), HttpStatus.OK );

        BeanUtils.copyProperties( srcContinent, targetContinent );
        locationService.updateContinent( targetContinent );

        ContinentDTO continentDTO = continentConverter.toDTO( targetContinent );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( continentDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( Response.notFound( "Continent was not found!" ), HttpStatus.OK );

        locationService.deleteContinent( continent );

        return new ResponseEntity<>( HttpStatus.OK );
    }
}
