package de.daxu.swamp.api.continent.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContinentParamConverter implements Converter<String, Continent> {

    private final LocationService locationService;

    @Autowired
    public ContinentParamConverter( LocationService locationService ) {
        this.locationService = locationService;
    }

    @Override
    public Continent convert( String source ) {
        Continent continent = locationService.getContinent( source );

        if( continent == null )
            throw new NotFoundException( "Continent was not found!" );

        return continent;
    }
}
