package de.daxu.swamp.deploy.result;

import de.daxu.swamp.deploy.container.ContainerId;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

import static de.daxu.swamp.deploy.result.CreateContainerResult.Builder.aCreateContainerResponse;

@Component
public class ContainerResultFactory {

    public ContainerResult createResponse( ContainerId containerId, Set<String> warnings ) {
        return new ContainerResult.Builder<>()
                .withContainerId( containerId )
                .withWarnings( warnings )
                .withTimestamp( new Date() )
                .build();
    }

    public CreateContainerResult createCreateContainerResponse( ContainerId containerId,
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
