package de.daxu.swamp.api.containertemplate.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.core.containertemplate.ContainerTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContainerTemplateParamConverter implements Converter<String, ContainerTemplate> {

    private final ContainerTemplateService containerTemplateService;

    @Autowired
    public ContainerTemplateParamConverter(ContainerTemplateService containerTemplateService) {
        this.containerTemplateService = containerTemplateService;
    }

    @Override
    public ContainerTemplate convert(String source) {
        ContainerTemplate containerTemplate = containerTemplateService.findContainerTemplate(source);

        if (containerTemplate == null)
            throw new NotFoundException("Container template was not found!");

        return containerTemplate;
    }
}
