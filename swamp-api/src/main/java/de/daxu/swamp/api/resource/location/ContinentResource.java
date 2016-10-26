package de.daxu.swamp.api.resource.location;

import de.daxu.swamp.api.converter.location.ContinentConverter;
import de.daxu.swamp.api.converter.location.ContinentCreateConverter;
import de.daxu.swamp.api.dto.location.ContinentCreateDTO;
import de.daxu.swamp.api.dto.location.ContinentDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
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

@RestController
@RequestMapping( CONTINENTS_URL )
public class ContinentResource {

    public static final String CONTINENTS_URL = "/continents";

    @Autowired
    ResponseFactory responseFactory;

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

        return new ResponseEntity<>( responseFactory.success( continents ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @RequestBody ContinentCreateDTO dto ) {

        Continent continent = continentCreateConverter.toDomain( dto );
        continent = locationService.createContinent( continent );

        return new ResponseEntity<>( responseFactory.success( continent ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.GET )
    public ResponseEntity<Response> get( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        ContinentDTO continentDTO = continentConverter.toDTO( continent );

        return new ResponseEntity<>( responseFactory.success( continentDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.PUT )
    public ResponseEntity<Response> put( @PathVariable( "continentId" ) String continentId,
                                         @RequestBody ContinentCreateDTO continentCreateDTO ) {

        Continent targetContinent = locationService.getContinent( continentId );
        Continent srcContinent = continentCreateConverter.toDomain( continentCreateDTO );

        if ( targetContinent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        BeanUtils.copyProperties( srcContinent, targetContinent );
        locationService.updateContinent( targetContinent );

        ContinentDTO continentDTO = continentConverter.toDTO( targetContinent );

        return new ResponseEntity<>( responseFactory.success( continentDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{continentId}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        locationService.deleteContinent( continent );

        return new ResponseEntity<>( responseFactory.success(), HttpStatus.OK );
    }
}
