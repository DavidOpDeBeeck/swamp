package de.daxu.swamp.deploy.result;

import de.daxu.swamp.deploy.container.ContainerId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

import static de.daxu.swamp.deploy.result.ContainerResult.Builder.aContainerResultBuilder;

@Component
public class ContainerResultFactory {

    public ContainerResult createResponse( ContainerId containerId, Set<String> warnings ) {
        return aContainerResultBuilder()
                .withContainerId( containerId )
                .withWarnings( warnings )
                .withTimestamp( LocalDateTime.now() )
                .build();
    }
}
