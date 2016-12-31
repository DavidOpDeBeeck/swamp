package de.daxu.swamp.deploy.result;

import de.daxu.swamp.deploy.container.ContainerId;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class ContainerResultFactory {

    public ContainerResult createResponse( ContainerId containerId, Set<String> warnings ) {
        return new ContainerResult.Builder<>()
                .withContainerId( containerId )
                .withWarnings( warnings )
                .withTimestamp( new Date() )
                .build();
    }
}
