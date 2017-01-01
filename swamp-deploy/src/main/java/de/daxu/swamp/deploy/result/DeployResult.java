package de.daxu.swamp.deploy.result;

import java.time.LocalDateTime;
import java.util.Set;

public abstract class DeployResult {

    private Set<String> warnings;
    private LocalDateTime timestamp;

    DeployResult( Set<String> warnings, LocalDateTime timestamp ) {
        this.warnings = warnings;
        this.timestamp = timestamp;
    }

    public Set<String> getWarnings() {
        return warnings;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return warnings.isEmpty();
    }

    @SuppressWarnings( "unchecked" )
    public abstract static class Builder<B extends Builder<B>> {

        Set<String> warnings;
        LocalDateTime timestamp;

        B withWarnings( Set<String> warnings ) {
            this.warnings = warnings;
            return ( B ) this;
        }

        B withTimestamp( LocalDateTime timestamp ) {
            this.timestamp = timestamp;
            return ( B ) this;
        }

        public abstract DeployResult build();
    }
}
