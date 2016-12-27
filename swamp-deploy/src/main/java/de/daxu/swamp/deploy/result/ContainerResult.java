package de.daxu.swamp.deploy.result;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Date;
import java.util.Set;

public class ContainerResult extends Result {

    private ContainerId containerId;

    ContainerResult( ContainerId containerId, Set<String> warnings, Date timestamp ) {
        super( warnings, timestamp );
        this.containerId = containerId;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    @SuppressWarnings( "unchecked" )
    public static class Builder<BUILDER extends Builder<BUILDER>> extends Result.Builder<BUILDER> {

        protected ContainerId containerId;

        public BUILDER withContainerId( ContainerId containerId ) {
            this.containerId = containerId;
            return ( BUILDER ) this;
        }

        @Override
        public ContainerResult build() {
            return new ContainerResult( containerId, warnings, timestamp );
        }
    }
}
