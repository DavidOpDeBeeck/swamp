package de.daxu.swamp.api.containerinstance.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceViewParamConverter implements Converter<String, ContainerInstanceView> {

    private final ContainerInstanceIdParamConverter idParamConverter;
    private final ContainerInstanceQueryService containerInstanceQueryService;

    @Autowired
    public ContainerInstanceViewParamConverter(ContainerInstanceIdParamConverter idParamConverter,
                                               ContainerInstanceQueryService containerInstanceQueryService) {
        this.idParamConverter = idParamConverter;
        this.containerInstanceQueryService = containerInstanceQueryService;
    }

    @Override
    public ContainerInstanceView convert(String source) {
        ContainerInstanceId id = idParamConverter.convert(source);
        ContainerInstanceView view = containerInstanceQueryService.getContainerInstanceViewById(id);

        if (view == null)
            throw new NotFoundException("Container instance was not found!");

        return view;
    }
}
