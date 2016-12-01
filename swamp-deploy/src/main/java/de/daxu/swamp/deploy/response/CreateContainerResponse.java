package de.daxu.swamp.deploy.response;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Date;
import java.util.Set;

public class CreateContainerResponse extends ContainerResponse {

    private String internalContainerId;

    private CreateContainerResponse( ContainerId containerId, Set<String> warnings, Date timestamp, String internalContainerId ) {
        super( containerId, warnings, timestamp );
        this.internalContainerId = internalContainerId;
    }

    public String getInternalContainerId() {
        return internalContainerId;
    }

    public static class Builder extends ContainerResponse.Builder<Builder> {

        private String internalContainerId;

        public static Builder aCreateContainerResponse() {
            return new Builder();
        }

        public Builder withInternalContainerId( String id ) {
            this.internalContainerId = id;
            return this;
        }

        @Override
        public CreateContainerResponse build() {
            return new CreateContainerResponse( containerId, warnings, timestamp, internalContainerId );
        }
    }
}
