package de.daxu.swamp.api.server;

import de.daxu.swamp.api.server.converter.ServerConverter;
import de.daxu.swamp.api.server.converter.ServerCreateConverter;
import de.daxu.swamp.api.server.dto.ServerCreateDTO;
import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.datacenter.DatacenterResource.DATACENTERS_URL;
import static de.daxu.swamp.api.server.ServerResource.SERVERS_URL;

@RestController
@RequestMapping(SERVERS_URL)
public class ServerResource {

    static final String SERVERS_URL = DATACENTERS_URL + "/{datacenterId}/servers";

    private final ResponseFactory responseFactory;
    private final LocationService locationService;
    private final ServerConverter serverConverter;
    private final ServerCreateConverter serverCreateConverter;

    @Autowired
    public ServerResource(ResponseFactory responseFactory,
                          LocationService locationService,
                          ServerConverter serverConverter,
                          ServerCreateConverter serverCreateConverter) {
        this.responseFactory = responseFactory;
        this.locationService = locationService;
        this.serverConverter = serverConverter;
        this.serverCreateConverter = serverCreateConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll(@PathVariable("datacenterId") Datacenter datacenter) {

        List<ServerDTO> servers = datacenter.getServers()
                .stream()
                .map(serverConverter::toDTO)
                .collect(Collectors.toList());

        return responseFactory.success(servers);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response post(@PathVariable("datacenterId") Datacenter datacenter,
                         @Valid @RequestBody ServerCreateDTO dto) {

        Server server = serverCreateConverter.toDomain(dto);
        server = locationService.addServerToDatacenter(datacenter, server);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(server.getId()).toUri();

        return responseFactory.created(location);
    }

    @RequestMapping(value = "/{serverId}", method = RequestMethod.GET)
    public Response get(@PathVariable("serverId") Server server) {

        return responseFactory.success(serverConverter.toDTO(server));
    }

    @RequestMapping(value = "/{serverId}", method = RequestMethod.PUT)
    public Response put(@PathVariable("serverId") Server serverToUpdate,
                        @Valid @RequestBody ServerCreateDTO updatedServerDTO) {

        Server updatedServer = serverCreateConverter.toDomain(updatedServerDTO);

        BeanUtils.copyPropertiesIgnoreNulls(updatedServer, serverToUpdate);
        locationService.updateServer(serverToUpdate);

        return responseFactory.success(serverConverter.toDTO(serverToUpdate));
    }

    @RequestMapping(value = "/{serverId}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("datacenterId") Datacenter datacenter,
                           @PathVariable("serverId") Server server) {

        locationService.removeServerFromDatacenter(datacenter, server);
        return responseFactory.success();
    }
}
