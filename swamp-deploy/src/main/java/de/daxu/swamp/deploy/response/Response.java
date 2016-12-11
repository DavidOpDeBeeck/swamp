package de.daxu.swamp.deploy.response;

import java.util.Date;
import java.util.Set;

public class Response {

    private Set<String> warnings;
    private Date timestamp;

    Response( Set<String> warnings, Date timestamp ) {
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

        public Response build() {
            return new Response( warnings, timestamp );
        }
    }
}
