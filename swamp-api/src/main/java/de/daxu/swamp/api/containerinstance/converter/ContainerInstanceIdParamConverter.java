package de.daxu.swamp.api.containerinstance.converter;

import de.daxu.swamp.common.web.exception.BadRequestException;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceIdParamConverter implements Converter<String, ContainerInstanceId> {

    @Override
    public ContainerInstanceId convert(String source) {
        ContainerInstanceId id;
        try {
            id = ContainerInstanceId.from(source);
        } catch (Exception e) {
            throw new BadRequestException("Container instance has an invalid format");
        }
        return id;
    }
}
