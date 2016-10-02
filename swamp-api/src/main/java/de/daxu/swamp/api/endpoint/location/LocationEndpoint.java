package de.daxu.swamp.api.endpoint.location;

import de.daxu.swamp.api.converter.location.LocationConverter;
import de.daxu.swamp.api.dto.location.LocationDTO;
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

@RestController
@RequestMapping( value = "/locations" )
public class LocationEndpoint {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationConverter locationConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<List<LocationDTO>> getAll() {
        return new ResponseEntity<>( locationService.getAllLocation()
                .stream()
                .map( locationConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<LocationDTO> get( @PathVariable( "id" ) String id ) {
        return new ResponseEntity<>( locationConverter.toDTO( locationService.getLocation( id ) ), HttpStatus.OK );
    }
}
