package de.daxu.swamp.api.endpoint.location;

import de.daxu.swamp.api.converter.location.*;
import de.daxu.swamp.api.dto.location.*;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.core.location.Datacenter;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/continents" )
public class ContinentEndpoint {

    @Autowired
    LocationService locationService;

    @Autowired
    ContinentConverter continentConverter;

    @Autowired
    DatacenterConverter datacenterConverter;

    @Autowired
    ServerConverter serverConverter;

    @Autowired
    ContinentCreateConverter continentCreateConverter;

    @Autowired
    DatacenterCreateConverter datacenterCreateConverter;

    @Autowired
    ServerCreateConverter serverCreateConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Collection<LocationDTO>> getAll() {
        return new ResponseEntity<>( locationService
                .getAllContinents()
                .stream()
                .map( continentConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<ContinentDTO> post( @RequestBody ContinentCreateDTO dto ) {
        Continent continent = locationService.createContinent( continentCreateConverter.toDomain( dto ) );
        return new ResponseEntity<>( continentConverter.toDTO( continent ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<ContinentDTO> get( @PathVariable( "id" ) String id ) {
        return new ResponseEntity<>( continentConverter.toDTO( locationService.getContinent( id ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public ResponseEntity<ContinentDTO> put( @PathVariable( "id" ) String id, @RequestBody ContinentCreateDTO dto ) {
        Continent oldContinent = locationService.getContinent( id );
        Continent newContinent = continentCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( newContinent, oldContinent );
        locationService.updateContinent( oldContinent );

        return new ResponseEntity<>( continentConverter.toDTO( oldContinent ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "id" ) String id ) {
        locationService.deleteContinent( locationService.getContinent( id ) );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters", method = RequestMethod.GET )
    public ResponseEntity<List<DatacenterDTO>> getDatacenters( @PathVariable( "id" ) String id ) {
        return new ResponseEntity<>( locationService.getContinent( id )
                .getDatacenters()
                .stream()
                .map( datacenterConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters", method = RequestMethod.POST )
    public ResponseEntity<DatacenterDTO> postDatacenter( @PathVariable( "id" ) String id, @RequestBody DatacenterCreateDTO dto ) {
        Continent continent = locationService.getContinent( id );
        Datacenter datacenter = locationService.addDatacenterToContinent( continent, datacenterCreateConverter.toDomain( dto ) );
        return new ResponseEntity<>( datacenterConverter.toDTO( datacenter ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}", method = RequestMethod.GET )
    public ResponseEntity<DatacenterDTO> getDatacenter( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId ) {
        return new ResponseEntity<>( datacenterConverter.toDTO( locationService.getDatacenter( datacenterId ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}", method = RequestMethod.PUT )
    public ResponseEntity<DatacenterDTO> putDatacenter( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId, @RequestBody DatacenterCreateDTO dto ) {
        Datacenter oldDatacenter = locationService.getDatacenter( datacenterId );
        Datacenter newDatacenter = datacenterCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( newDatacenter, oldDatacenter );
        locationService.updateDatacenter( oldDatacenter );

        return new ResponseEntity<>( datacenterConverter.toDTO( oldDatacenter ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}", method = RequestMethod.DELETE )
    public ResponseEntity deleteDatacenter( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId ) {
        Continent continent = locationService.getContinent( id );
        Datacenter datacenter = locationService.getDatacenter( datacenterId );
        locationService.removeDatacenterFromContinent( continent, datacenter );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}/servers", method = RequestMethod.GET )
    public ResponseEntity<List<ServerDTO>> getServers( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId ) {
        return new ResponseEntity<>( locationService.getDatacenter( datacenterId )
                .getServers()
                .stream()
                .map( serverConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}/servers", method = RequestMethod.POST )
    public ResponseEntity<ServerDTO> postServer( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId, @RequestBody ServerCreateDTO dto ) {
        Datacenter datacenter = locationService.getDatacenter( datacenterId );
        Server server = locationService.addServerToDatacenter( datacenter, serverCreateConverter.toDomain( dto ) );
        return new ResponseEntity<>( serverConverter.toDTO( server ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}/servers/{serverId}", method = RequestMethod.GET )
    public ResponseEntity<ServerDTO> getServer( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId, @PathVariable( "serverId" ) String serverId ) {
        return new ResponseEntity<>( serverConverter.toDTO( locationService.getServer( serverId ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}/servers/{serverId}", method = RequestMethod.PUT )
    public ResponseEntity<ServerDTO> putDatacenter( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId, @PathVariable( "serverId" ) String serverId, @RequestBody ServerCreateDTO dto ) {
        Server oldServer = locationService.getServer( serverId );
        Server newServer = serverCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( newServer, oldServer );
        locationService.updateServer( oldServer );

        return new ResponseEntity<>( serverConverter.toDTO( oldServer ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/datacenters/{datacenterId}/servers/{serverId}", method = RequestMethod.DELETE )
    public ResponseEntity deleteServer( @PathVariable( "id" ) String id, @PathVariable( "datacenterId" ) String datacenterId, @PathVariable( "serverId" ) String serverId ) {
        Datacenter datacenter = locationService.getDatacenter( datacenterId );
        Server server = locationService.getServer( serverId );
        locationService.removeServerFromDatacenter( datacenter, server );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
