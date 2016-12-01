package de.daxu.swamp.deploy.response;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Date;
import java.util.Set;

public class ContainerResponse {

    private ContainerId containerId;
    private Set<String> warnings;
    private Date timestamp;

    ContainerResponse( ContainerId containerId, Set<String> warnings, Date timestamp ) {
        this.containerId = containerId;
        this.warnings = warnings;
        this.timestamp = timestamp;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    public Set<String> getWarnings() {
        return warnings;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean success() {
        return warnings.isEmpty();
    }

    @SuppressWarnings( "unchecked" )
    public static class Builder<BUILDER extends Builder<BUILDER>> {

        ContainerId containerId;
        Set<String> warnings;
        Date timestamp;

        public static Builder aContainerResponse() {
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

        public BUILDER withWarnings( Set<String> warnings ) {
            this.warnings = warnings;
            return ( BUILDER ) this;
        }

        public BUILDER withTimestamp( Date timestamp ) {
            this.timestamp = timestamp;
            return ( BUILDER ) this;
        }

        public ContainerResponse build() {
            return new ContainerResponse( containerId, warnings, timestamp );
        }
    }
}
