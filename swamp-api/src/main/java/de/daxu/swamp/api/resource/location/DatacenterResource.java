package de.daxu.swamp.api.resource.location;

import de.daxu.swamp.api.converter.location.DatacenterConverter;
import de.daxu.swamp.api.converter.location.DatacenterCreateConverter;
import de.daxu.swamp.api.dto.location.DatacenterCreateDTO;
import de.daxu.swamp.api.dto.location.DatacenterDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.core.location.Datacenter;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.location.ContinentResource.CONTINENTS_URL;
import static de.daxu.swamp.api.resource.location.DatacenterResource.DATACENTERS_URL;

@RestController
@RequestMapping( DATACENTERS_URL )
public class DatacenterResource {

    public static final String DATACENTERS_URL = CONTINENTS_URL + "/{continentId}/datacenters";

    @Autowired
    ResponseFactory responseFactory;

    @Autowired
    LocationService locationService;

    @Autowired
    DatacenterConverter datacenterConverter;

    @Autowired
    DatacenterCreateConverter datacenterCreateConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> getDatacenters( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        List<DatacenterDTO> containers = continent.getDatacenters()
                .stream()
                .map( datacenterConverter::toDTO )
                .collect( Collectors.toList() );

        return new ResponseEntity<>( responseFactory.success( containers ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> postDatacenter( @PathVariable( "continentId" ) String continentId,
                                                    @RequestBody DatacenterCreateDTO dto ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = datacenterCreateConverter.toDomain( dto );
        datacenter = locationService.addDatacenterToContinent( continent, datacenter );
        DatacenterDTO datacenterDTO = datacenterConverter.toDTO( datacenter );

        return new ResponseEntity<>( responseFactory.success( datacenterDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.GET )
    public ResponseEntity<Response> getDatacenter( @PathVariable( "continentId" ) String continentId,
                                                   @PathVariable( "datacenterId" ) String datacenterId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        DatacenterDTO datacenterDTO = datacenterConverter.toDTO( datacenter );

        return new ResponseEntity<>( responseFactory.success( datacenterDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putDatacenter( @PathVariable( "continentId" ) String continentId,
                                                   @PathVariable( "datacenterId" ) String datacenterId,
                                                   @RequestBody DatacenterCreateDTO datacenterCreateDTO ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter targetDatacenter = locationService.getDatacenter( datacenterId );

        if ( targetDatacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        Datacenter srcDatacenter = datacenterCreateConverter.toDomain( datacenterCreateDTO );

        BeanUtils.copyProperties( srcDatacenter, targetDatacenter );
        locationService.updateDatacenter( targetDatacenter );

        DatacenterDTO datacenterDTO = datacenterConverter.toDTO( targetDatacenter );

        return new ResponseEntity<>( responseFactory.success( datacenterDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.DELETE )
    public ResponseEntity deleteDatacenter( @PathVariable( "continentId" ) String continentId,
                                            @PathVariable( "datacenterId" ) String datacenterId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        locationService.removeDatacenterFromContinent( continent, datacenter );

        return new ResponseEntity<>( responseFactory.success(), HttpStatus.OK );
    }
}
