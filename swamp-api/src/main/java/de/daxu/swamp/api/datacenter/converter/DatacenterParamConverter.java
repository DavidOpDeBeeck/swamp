package de.daxu.swamp.api.datacenter.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DatacenterParamConverter implements Converter<String, Datacenter> {

    private final LocationService locationService;

    @Autowired
    public DatacenterParamConverter( LocationService locationService ) {
        this.locationService = locationService;
    }

    @Override
    public Datacenter convert( String source ) {
        Datacenter datacenter = locationService.getDatacenter( source );

        if( datacenter == null )
            throw new NotFoundException( "Datacenter was not found!" );

        return datacenter;
    }
}
