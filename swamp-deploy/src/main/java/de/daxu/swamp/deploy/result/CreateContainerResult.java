package de.daxu.swamp.deploy.result;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Date;
import java.util.Set;

public class CreateContainerResult extends ContainerResult {

    private String internalContainerId;

    private CreateContainerResult( ContainerId containerId, Set<String> warnings, Date timestamp, String internalContainerId ) {
        super( containerId, warnings, timestamp );
        this.internalContainerId = internalContainerId;
    }

    public String getInternalContainerId() {
        return internalContainerId;
    }

    public static class Builder extends ContainerResult.Builder<Builder> {

        private String internalContainerId;

        public static Builder aCreateContainerResponse() {
            return new Builder();
        }

        public Builder withInternalContainerId( String id ) {
            this.internalContainerId = id;
            return this;
        }

        @Override
        public CreateContainerResult build() {
            return new CreateContainerResult( containerId, warnings, timestamp, internalContainerId );
        }
    }
}
