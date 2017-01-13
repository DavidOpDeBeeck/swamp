package de.daxu.swamp.common.web.response;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;

import java.net.URI;
import java.time.LocalDateTime;

@JsonInclude( JsonInclude.Include.NON_NULL )
@SuppressWarnings( "unused" )
public class Meta {

    private int status;
    private String version;
    private LocalDateTime requestedAt;
    private String location;

    @JsonCreator
    private Meta( @JsonProperty( "status" ) int status,
                  @JsonProperty( "version" ) String version,
                  @JsonProperty( "requestedAt" ) LocalDateTime requestedAt,
                  @JsonProperty( "location" ) String location ) {
        this.status = status;
        this.version = version;
        this.requestedAt = requestedAt;
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    @JsonSerialize( using = LocalDateTimeSerializer.class )
    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public String getLocation() {
        return location;
    }

    public static class Builder {

        private int status;
        private String version;
        private LocalDateTime timestamp;
        private String location;

        public Builder withSuccess() {
            this.timestamp = LocalDateTime.now();
            this.status = 200;
            return this;
        }

        public Builder withCreated( URI location ) {
            this.timestamp = LocalDateTime.now();
            this.status = 201;
            this.location = location.toString();
            return this;
        }

        public Builder withBadRequest() {
            this.timestamp = LocalDateTime.now();
            this.status = 400;
            return this;
        }

        public Builder withForbidden() {
            this.timestamp = LocalDateTime.now();
            this.status = 403;
            return this;
        }

        public Builder withServerError() {
            this.timestamp = LocalDateTime.now();
            this.status = 500;
            return this;
        }

        public Builder withNotFound() {
            this.timestamp = LocalDateTime.now();
            this.status = 404;
            return this;
        }

        public Builder withVersion( String version ) {
            this.version = version;
            return this;
        }

        public Meta build() {
            return new Meta( status, version, timestamp, location );
        }
    }
}