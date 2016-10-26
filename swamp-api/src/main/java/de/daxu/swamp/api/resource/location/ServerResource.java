package de.daxu.swamp.api.resource.location;

import de.daxu.swamp.api.converter.location.ServerConverter;
import de.daxu.swamp.api.converter.location.ServerCreateConverter;
import de.daxu.swamp.api.dto.location.ServerCreateDTO;
import de.daxu.swamp.api.dto.location.ServerDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.core.location.Datacenter;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.location.DatacenterResource.DATACENTERS_URL;
import static de.daxu.swamp.api.resource.location.ServerResource.SERVERS_URL;

@RestController
@RequestMapping( SERVERS_URL )
public class ServerResource {

    public static final String SERVERS_URL = DATACENTERS_URL + "/{datacenterId}/servers";

    @Autowired
    ResponseFactory responseFactory;

    @Autowired
    LocationService locationService;

    @Autowired
    ServerConverter serverConverter;

    @Autowired
    ServerCreateConverter serverCreateConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> getServers( @PathVariable( "continentId" ) String continentId,
                                                @PathVariable( "datacenterId" ) String datacenterId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        List<ServerDTO> servers = datacenter.getServers()
                .stream()
                .map( serverConverter::toDTO )
                .collect( Collectors.toList() );

        return new ResponseEntity<>( responseFactory.success( servers ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> postServer( @PathVariable( "continentId" ) String continentId,
                                                @PathVariable( "datacenterId" ) String datacenterId,
                                                @RequestBody ServerCreateDTO serverCreateDTO ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        Server server = serverCreateConverter.toDomain( serverCreateDTO );
        server = locationService.addServerToDatacenter( datacenter, server );
        ServerDTO serverDTO = serverConverter.toDTO( server );

        return new ResponseEntity<>( responseFactory.success( serverDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{serverId}", method = RequestMethod.GET )
    public ResponseEntity<Response> getServer( @PathVariable( "continentId" ) String continentId,
                                               @PathVariable( "datacenterId" ) String datacenterId,
                                               @PathVariable( "serverId" ) String serverId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        Server server = locationService.getServer( serverId );

        if ( server == null )
            return new ResponseEntity<>( responseFactory.notFound( "Server was not found!" ), HttpStatus.OK );

        ServerDTO serverDTO = serverConverter.toDTO( server );

        return new ResponseEntity<>( responseFactory.success( serverDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{serverId}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putDatacenter( @PathVariable( "continentId" ) String continentId,
                                                   @PathVariable( "datacenterId" ) String datacenterId,
                                                   @PathVariable( "serverId" ) String serverId,
                                                   @RequestBody ServerCreateDTO serverCreateDTO ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        Server targetServer = locationService.getServer( serverId );

        if ( targetServer == null )
            return new ResponseEntity<>( responseFactory.notFound( "Server was not found!" ), HttpStatus.OK );

        Server srcServer = serverCreateConverter.toDomain( serverCreateDTO );

        BeanUtils.copyProperties( srcServer, targetServer );
        locationService.updateServer( targetServer );

        ServerDTO serverDTO = serverConverter.toDTO( targetServer );

        return new ResponseEntity<>( responseFactory.success( serverDTO ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{serverId}", method = RequestMethod.DELETE )
    public ResponseEntity deleteServer( @PathVariable( "continentId" ) String continentId,
                                        @PathVariable( "datacenterId" ) String datacenterId,
                                        @PathVariable( "serverId" ) String serverId ) {

        Continent continent = locationService.getContinent( continentId );

        if ( continent == null )
            return new ResponseEntity<>( responseFactory.notFound( "Continent was not found!" ), HttpStatus.OK );

        Datacenter datacenter = locationService.getDatacenter( datacenterId );

        if ( datacenter == null )
            return new ResponseEntity<>( responseFactory.notFound( "Datacenter was not found!" ), HttpStatus.OK );

        Server server = locationService.getServer( serverId );

        if ( server == null )
            return new ResponseEntity<>( responseFactory.notFound( "Server was not found!" ), HttpStatus.OK );

        locationService.removeServerFromDatacenter( datacenter, server );

        return new ResponseEntity<>( responseFactory.success(), HttpStatus.OK );
    }
}
