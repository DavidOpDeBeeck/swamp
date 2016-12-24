package de.daxu.swamp.api.datacenter;

import de.daxu.swamp.api.datacenter.converter.DatacenterConverter;
import de.daxu.swamp.api.datacenter.converter.DatacenterCreateConverter;
import de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTO;
import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
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
    public Response getAll( @PathVariable( "continentId" ) Continent continent ) {

        List<DatacenterDTO> containers = continent.getDatacenters()
                .stream()
                .map( datacenterConverter::toDTO )
                .collect( Collectors.toList() );

        return response.success( containers );
    }

    @RequestMapping( method = RequestMethod.POST )
    public Response post( @PathVariable( "continentId" ) Continent continent,
                          @RequestBody DatacenterCreateDTO dto ) {

        Datacenter datacenter = datacenterCreateConverter.toDomain( dto );
        datacenter = locationService.addDatacenterToContinent( continent, datacenter );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( datacenter.getId() ).toUri();

        return response.created( location );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "datacenterId" ) Datacenter datacenter ) {

        return response.success( datacenterConverter.toDTO( datacenter ) );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.PUT )
    public Response put( @PathVariable( "datacenterId" ) Datacenter datacenterToUpdate,
                         @RequestBody DatacenterCreateDTO updatedDatacenterDTO ) {


        Datacenter updatedDatacenter = datacenterCreateConverter.toDomain( updatedDatacenterDTO );

        BeanUtils.copyProperties( updatedDatacenter, datacenterToUpdate );
        locationService.updateDatacenter( datacenterToUpdate );

        return response.success( datacenterConverter.toDTO( datacenterToUpdate ) );
    }

    @RequestMapping( value = "/{datacenterId}", method = RequestMethod.DELETE )
    public Response delete( @PathVariable( "continentId" ) Continent continent,
                            @PathVariable( "datacenterId" ) Datacenter datacenter ) {

        locationService.removeDatacenterFromContinent( continent, datacenter );
        return response.success();
    }
}
