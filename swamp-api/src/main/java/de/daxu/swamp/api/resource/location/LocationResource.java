package de.daxu.swamp.api.resource.location;

import de.daxu.swamp.api.converter.location.LocationConverter;
import de.daxu.swamp.api.dto.location.LocationDTO;
import de.daxu.swamp.common.response.Meta;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.location.LocationResource.LOCATIONS_URL;
import static de.daxu.swamp.common.response.Response.Builder.aResponse;

@RestController
@RequestMapping( LOCATIONS_URL )
public class LocationResource {

    public static final String LOCATIONS_URL = "/locations";

    @Autowired
    LocationService locationService;

    @Autowired
    LocationConverter locationConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> getAll() {

        List<LocationDTO> servers = locationService.getAllLocation()
                .stream()
                .map( locationConverter::toDTO )
                .collect( Collectors.toList() );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( servers )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }
}
