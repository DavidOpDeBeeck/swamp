package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.response.ContainerResponse;

import java.util.function.Consumer;

public class LogContainerConfiguration extends Configuration {

    private Consumer<String> logCallback;

    LogContainerConfiguration( ContainerId containerId, Server server, Consumer<String> logCallback ) {
        super( containerId, server );
        this.logCallback = logCallback;
    }

    public Consumer<String> getLogCallback() {
        return logCallback;
    }

    public static class Builder extends Configuration.Builder<Builder> {

        private Consumer<String> logCallback;

        public static ContainerResponse.Builder aLogContainerConfiguration() {
            return new ContainerResponse.Builder();
        }

        public Builder withLogCallback( Consumer<String> logCallback ) {
            this.logCallback = logCallback;
            return this;
        }

        @Override
        public Configuration build() {
            return new LogContainerConfiguration( containerId, server, logCallback );
        }
    }
}
