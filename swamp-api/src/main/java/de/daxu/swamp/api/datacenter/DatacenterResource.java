package de.daxu.swamp.api.datacenter;

import de.daxu.swamp.api.datacenter.converter.DatacenterConverter;
import de.daxu.swamp.api.datacenter.converter.DatacenterCreateConverter;
import de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTO;
import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.continent.ContinentResource.CONTINENTS_URL;
import static de.daxu.swamp.api.datacenter.DatacenterResource.DATACENTERS_URL;

@RestController
@RequestMapping( DATACENTERS_URL )
public class DatacenterResource {

    public static final String DATACENTERS_URL = CONTINENTS_URL + "/{continentId}/datacenters";

    private final ResponseFactory response;
    private final LocationService locationService;
    private final DatacenterConverter datacenterConverter;
    private final DatacenterCreateConverter datacenterCreateConverter;

    @Autowired
    public DatacenterResource( ResponseFactory responseFactory,
                               LocationService locationService,
                               DatacenterConverter datacenterConverter,
                               DatacenterCreateConverter datacenterCreateConverter ) {
        this.response = responseFactory;
        this.locationService = locationService;
        this.datacenterConverter = datacenterConverter;
        this.datacenterCreateConverter = datacenterCreateConverter;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getDatacenters( @PathVariable( "continentId" ) String continentId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        List<DatacenterDTO> containers = continent.getDatacenters()
                .stream()
                .map( datacenterConverter::toDTO )
                .collect( Collectors.toList() );

        return response.success( containers );
    }

    @RequestMapping( method = RequestMethod.POST )
    public Response postDatacenter( @PathVariable( "continentId" ) String continentId,
                                    @RequestBody DatacenterCreateDTO dto ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        Datacenter datacenter = datacenterCreateConverter.toDomain( dto );
        datacenter = locationService.addDatacenterToContinent( continent, datacenter );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( datacenter.getId() ).toUri();

        return response.created( location );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.GET )
    public Response getDatacenter( @PathVariable( "continentId" ) String continentId,
                                   @PathVariable( "datacenterId" ) String datacenterId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return response.notFound( "Datacenter was not found!" );

        DatacenterDTO datacenterDTO = datacenterConverter.toDTO( datacenter );

        return response.success( datacenterDTO );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.PUT )
    public Response putDatacenter( @PathVariable( "continentId" ) String continentId,
                                   @PathVariable( "datacenterId" ) String datacenterId,
                                   @RequestBody DatacenterCreateDTO datacenterCreateDTO ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        Datacenter targetDatacenter = locationService.getDatacenter( datacenterId );

        if( targetDatacenter == null )
            return response.notFound( "Datacenter was not found!" );

        Datacenter srcDatacenter = datacenterCreateConverter.toDomain( datacenterCreateDTO );

        BeanUtils.copyProperties( srcDatacenter, targetDatacenter );
        locationService.updateDatacenter( targetDatacenter );

        DatacenterDTO datacenterDTO = datacenterConverter.toDTO( targetDatacenter );

        return response.success( datacenterDTO );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.DELETE )
    public Response deleteDatacenter( @PathVariable( "continentId" ) String continentId,
                                            @PathVariable( "datacenterId" ) String datacenterId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return response.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return response.notFound( "Datacenter was not found!" );

        locationService.removeDatacenterFromContinent( continent, datacenter );

        return response.success();
    }
}
