package de.daxu.swamp.api.server;

import de.daxu.swamp.api.server.converter.ServerConverter;
import de.daxu.swamp.api.server.converter.ServerCreateConverter;
import de.daxu.swamp.api.server.dto.ServerCreateDTO;
import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.location.continent.Continent;
import de.daxu.swamp.core.location.datacenter.Datacenter;
import de.daxu.swamp.core.location.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.datacenter.DatacenterResource.DATACENTERS_URL;
import static de.daxu.swamp.api.server.ServerResource.SERVERS_URL;

@RestController
@RequestMapping( SERVERS_URL )
public class ServerResource {

    public static final String SERVERS_URL = DATACENTERS_URL + "/{datacenterId}/servers";

    private final ResponseFactory responseFactory;
    private final LocationService locationService;
    private final ServerConverter serverConverter;
    private final ServerCreateConverter serverCreateConverter;

    @Autowired
    public ServerResource( ResponseFactory responseFactory,
                           LocationService locationService,
                           ServerConverter serverConverter,
                           ServerCreateConverter serverCreateConverter ) {
        this.responseFactory = responseFactory;
        this.locationService = locationService;
        this.serverConverter = serverConverter;
        this.serverCreateConverter = serverCreateConverter;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getServers( @PathVariable( "continentId" ) String continentId,
                                @PathVariable( "datacenterId" ) String datacenterId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return responseFactory.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return responseFactory.notFound( "Datacenter was not found!" );

        List<ServerDTO> servers = datacenter.getServers()
                .stream()
                .map( serverConverter::toDTO )
                .collect( Collectors.toList() );

        return responseFactory.success( servers );
    }

    @RequestMapping( method = RequestMethod.POST )
    public Response postServer( @PathVariable( "continentId" ) String continentId,
                                @PathVariable( "datacenterId" ) String datacenterId,
                                @RequestBody ServerCreateDTO serverCreateDTO ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return responseFactory.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return responseFactory.notFound( "Datacenter was not found!" );

        Server server = serverCreateConverter.toDomain( serverCreateDTO );
        server = locationService.addServerToDatacenter( datacenter, server );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( server.getId() ).toUri();

        return responseFactory.created( location );
    }

    @RequestMapping( value = "/{serverId}", method = RequestMethod.GET )
    public Response getServer( @PathVariable( "continentId" ) String continentId,
                               @PathVariable( "datacenterId" ) String datacenterId,
                               @PathVariable( "serverId" ) String serverId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return responseFactory.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return responseFactory.notFound( "Datacenter was not found!" );

        Server server = locationService.getServer( serverId );

        if( server == null )
            return responseFactory.notFound( "Server was not found!" );

        ServerDTO serverDTO = serverConverter.toDTO( server );

        return responseFactory.success( serverDTO );
    }

    @RequestMapping( value = "/{serverId}", method = RequestMethod.PUT )
    public Response putDatacenter( @PathVariable( "continentId" ) String continentId,
                                   @PathVariable( "datacenterId" ) String datacenterId,
                                   @PathVariable( "serverId" ) String serverId,
                                   @RequestBody ServerCreateDTO serverCreateDTO ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return responseFactory.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return responseFactory.notFound( "Datacenter was not found!" );

        Server targetServer = locationService.getServer( serverId );

        if( targetServer == null )
            return responseFactory.notFound( "Server was not found!" );

        Server srcServer = serverCreateConverter.toDomain( serverCreateDTO );

        BeanUtils.copyProperties( srcServer, targetServer );
        locationService.updateServer( targetServer );

        ServerDTO serverDTO = serverConverter.toDTO( targetServer );

        return responseFactory.success( serverDTO );
    }

    @RequestMapping( value = "/{serverId}", method = RequestMethod.DELETE )
    public Response deleteServer( @PathVariable( "continentId" ) String continentId,
                                        @PathVariable( "datacenterId" ) String datacenterId,
                                        @PathVariable( "serverId" ) String serverId ) {

        Continent continent = locationService.getContinent( continentId );

        if( continent == null )
            return responseFactory.notFound( "Continent was not found!" );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if( datacenter == null )
            return responseFactory.notFound( "Datacenter was not found!" );

        Server server = locationService.getServer( serverId );

        if( server == null )
            return responseFactory.notFound( "Server was not found!" );

        locationService.removeServerFromDatacenter( datacenter, server );

        return responseFactory.success();
    }
}
