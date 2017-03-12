package de.daxu.swamp.api.continent;

import de.daxu.swamp.api.continent.converter.ContinentConverter;
import de.daxu.swamp.api.continent.converter.ContinentCreateConverter;
import de.daxu.swamp.api.continent.dto.ContinentCreateDTO;
import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.continent.ContinentResource.CONTINENTS_URL;

@RestController
@RequestMapping(CONTINENTS_URL)
public class ContinentResource {

    public static final String CONTINENTS_URL = "/continents";

    private final ResponseFactory response;
    private final LocationService locationService;
    private final ContinentConverter continentConverter;
    private final ContinentCreateConverter continentCreateConverter;

    @Autowired
    public ContinentResource(ResponseFactory responseFactory,
                             LocationService locationService,
                             ContinentConverter continentConverter,
                             ContinentCreateConverter continentCreateConverter) {
        this.response = responseFactory;
        this.locationService = locationService;
        this.continentConverter = continentConverter;
        this.continentCreateConverter = continentCreateConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll() {

        List<ContinentDTO> continents = locationService
                .getAllContinents()
                .stream()
                .map(continentConverter::toDTO)
                .collect(Collectors.toList());

        return response.success(continents);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response post(@Valid @RequestBody ContinentCreateDTO dto) {

        Continent continent = continentCreateConverter.toDomain(dto);
        continent = locationService.createContinent(continent);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(continent.getId()).toUri();

        return response.created(location);
    }

    @RequestMapping(value = "/{continentId}", method = RequestMethod.GET)
    public Response get(@PathVariable("continentId") Continent continent) {

        return response.success(continentConverter.toDTO(continent));
    }

    @RequestMapping(value = "/{continentId}", method = RequestMethod.PUT)
    public Response put(@PathVariable("continentId") Continent continentToUpdate,
                        @Valid @RequestBody ContinentCreateDTO updatedContinentDTO) {

        Continent updatedContinent = continentCreateConverter.toDomain(updatedContinentDTO);

        BeanUtils.copyPropertiesIgnoreNulls(updatedContinent, continentToUpdate);
        locationService.updateContinent(continentToUpdate);

        return response.success(continentConverter.toDTO(continentToUpdate));
    }

    @RequestMapping(value = "/{continentId}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("continentId") Continent continent) {

        locationService.deleteContinent(continent);
        return response.success();
    }
}
