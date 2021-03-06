package de.daxu.swamp.api.location;

import de.daxu.swamp.api.location.converter.LocationConverter;
import de.daxu.swamp.api.location.dto.LocationDTO;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.core.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.location.LocationResource.LOCATIONS_URL;

@RestController
@RequestMapping(LOCATIONS_URL)
public class LocationResource {

    static final String LOCATIONS_URL = "/locations";

    private final ResponseFactory responseFactory;
    private final LocationService locationService;
    private final LocationConverter locationConverter;

    @Autowired
    public LocationResource(ResponseFactory responseFactory,
                            LocationService locationService,
                            LocationConverter locationConverter) {
        this.responseFactory = responseFactory;
        this.locationService = locationService;
        this.locationConverter = locationConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll() {

        List<LocationDTO> locations = locationService.getAllLocation()
                .stream()
                .map(locationConverter::toDTO)
                .collect(Collectors.toList());

        return responseFactory.success(locations);
    }
}
