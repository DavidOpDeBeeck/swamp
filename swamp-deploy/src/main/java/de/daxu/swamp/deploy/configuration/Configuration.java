package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.response.ContainerResponse;

public class Configuration {

    private ContainerId containerId;
    private Server server;

    Configuration( ContainerId containerId, Server server ) {
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

        public static ContainerResponse.Builder aConfiguration() {
            return new ContainerResponse.Builder();
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

        public Configuration build() {
            return new Configuration( containerId, server );
        }
    }
}
