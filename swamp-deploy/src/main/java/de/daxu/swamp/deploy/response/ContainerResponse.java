package de.daxu.swamp.deploy.response;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Date;
import java.util.Set;

public class ContainerResponse extends Response {

    private ContainerId containerId;

    ContainerResponse( ContainerId containerId, Set<String> warnings, Date timestamp ) {
        super( warnings, timestamp );
        this.containerId = containerId;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    @SuppressWarnings( "unchecked" )
    public static class Builder<BUILDER extends Builder<BUILDER>> extends Response.Builder<BUILDER> {

        protected ContainerId containerId;

        public BUILDER withContainerId( ContainerId containerId ) {
            this.containerId = containerId;
            return ( BUILDER ) this;
        }

        @Override
        public ContainerResponse build() {
            return new ContainerResponse( containerId, warnings, timestamp );
        }
    }
}
