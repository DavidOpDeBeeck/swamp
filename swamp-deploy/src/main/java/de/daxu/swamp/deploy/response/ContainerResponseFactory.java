package de.daxu.swamp.deploy.response;

import de.daxu.swamp.deploy.container.ContainerId;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

import static de.daxu.swamp.deploy.response.ContainerResponse.Builder.aContainerResponse;
import static de.daxu.swamp.deploy.response.CreateContainerResponse.Builder.aCreateContainerResponse;

@Component
public class ContainerResponseFactory {

    public ContainerResponse createResponse( ContainerId containerId,
                                             Set<String> warnings ) {
        return aContainerResponse()
                .withContainerId( containerId )
                .withWarnings( warnings )
                .withTimestamp( new Date() )
                .build();
    }

    public CreateContainerResponse createCreateContainerResponse( ContainerId containerId,
                                                                  String internalContainerId,
                                                                  Set<String> warnings ) {
        return aCreateContainerResponse()
                .withContainerId( containerId )
                .withInternalContainerId( internalContainerId )
                .withWarnings( warnings )
                .withTimestamp( new Date() )
                .build();
    }
}
