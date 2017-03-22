package de.daxu.swamp.api.continent.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.continent.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContinentParamConverter implements Converter<String, Continent> {

    private final ContinentService continentService;

    @Autowired
    public ContinentParamConverter(ContinentService continentService) {
        this.continentService = continentService;
    }

    @Override
    public Continent convert(String source) {
        Continent continent = continentService.getContinent(source);

        if (continent == null)
            throw new NotFoundException("Continent was not found!");

        return continent;
    }
}
