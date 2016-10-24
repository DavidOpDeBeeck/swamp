package de.daxu.swamp.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.Collection;

import static de.daxu.swamp.common.response.Response.Builder.aResponse;

@JsonInclude( JsonInclude.Include.NON_NULL )
@SuppressWarnings( "unused" )
public class Response {

    private Meta meta;
    private Object data;
    private Collection<String> errors;

    private Response( Meta meta, Object data, Collection<String> errors ) {
        this.meta = meta;
        this.data = data;
        this.errors = errors;
    }

    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }

    public Collection<String> getErrors() {
        return errors;
    }

    public static Response notFound( String error ) {
        return aResponse()
                .withMeta( Meta.notFound() )
                .withErrors( error )
                .build();
    }

    public static Response badRequest( String error ) {
        return aResponse()
                .withMeta( Meta.badRequest() )
                .withErrors( error )
                .build();
    }

    public static Response success() {
        return aResponse()
                .withMeta( Meta.success() )
                .build();
    }

    public static class Builder {

        private Meta meta;
        private Object data;
        private Collection<String> errors;

        public static Builder aResponse() {
            return new Builder();
        }

        public Builder withMeta( Meta meta ) {
            this.meta = meta;
            return this;
        }

        public Builder withData( Object data ) {
            this.data = data;
            return this;
        }

        public Builder withErrors( String... errors ) {
            this.errors = Arrays.asList( errors );
            return this;
        }

        public Builder withErrors( Collection<String> errors ) {
            this.errors = errors;
            return this;
        }

        public Response build() {
            return new Response( meta, data, errors );
        }
    }
}
