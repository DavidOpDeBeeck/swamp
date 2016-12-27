package de.daxu.swamp.deploy.result;

import java.util.Date;
import java.util.Set;

public class Result {

    private Set<String> warnings;
    private Date timestamp;

    Result( Set<String> warnings, Date timestamp ) {
        this.warnings = warnings;
        this.timestamp = timestamp;
    }

    public Set<String> getWarnings() {
        return warnings;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return warnings.isEmpty();
    }

    @SuppressWarnings( "unchecked" )
    public static class Builder<BUILDER extends Builder<BUILDER>> {

        protected Set<String> warnings;
        protected Date timestamp;

        public static Builder aResponse() {
            return new Builder();
        }

        public BUILDER withWarnings( Set<String> warnings ) {
            this.warnings = warnings;
            return ( BUILDER ) this;
        }

        public BUILDER withTimestamp( Date timestamp ) {
            this.timestamp = timestamp;
            return ( BUILDER ) this;
        }

        public Result build() {
            return new Result( warnings, timestamp );
        }
    }
}
