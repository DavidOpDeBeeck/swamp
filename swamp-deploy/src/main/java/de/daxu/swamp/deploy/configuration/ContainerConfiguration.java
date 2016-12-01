package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.response.ContainerResponse;

public class ContainerConfiguration {

    private ContainerId containerId;
    private Server server;

    ContainerConfiguration( ContainerId containerId, Server server ) {
        this.containerId = containerId;
        this.server = server;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    public Server getServer() {
        return server;
    }

    @SuppressWarnings( "unchecked" )
    public static class Builder<BUILDER extends Builder<BUILDER>> {

        ContainerId containerId;
        Server server;

        public static Builder aContainerConfiguration() {
            return new Builder();
        }

        public BUILDER withContainerId( String containerId ) {
            this.containerId = ContainerId.of( containerId );
            return ( BUILDER ) this;
        }

        public BUILDER withContainerId( ContainerId containerId ) {
            this.containerId = containerId;
            return ( BUILDER ) this;
        }

        public BUILDER withServer( Server server ) {
            this.server = server;
            return ( BUILDER ) this;
        }

        public ContainerConfiguration build() {
            return new ContainerConfiguration( containerId, server );
        }
    }
}
