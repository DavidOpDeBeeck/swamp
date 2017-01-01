package de.daxu.swamp.deploy.result;

import de.daxu.swamp.deploy.container.ContainerId;

import java.time.LocalDateTime;
import java.util.Set;

public class ContainerResult extends DeployResult {

    private ContainerId containerId;

    private ContainerResult( ContainerId containerId, Set<String> warnings, LocalDateTime timestamp ) {
        super( warnings, timestamp );
        this.containerId = containerId;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    @SuppressWarnings( "unchecked" )
    public static class Builder extends DeployResult.Builder<ContainerResult.Builder> {

        private ContainerId containerId;

        static Builder aContainerResultBuilder() {
            return new Builder();
        }

        Builder withContainerId( ContainerId containerId ) {
            this.containerId = containerId;
            return this;
        }

        @Override
        public ContainerResult build() {
            return new ContainerResult( containerId, warnings, timestamp );
        }
    }
}
