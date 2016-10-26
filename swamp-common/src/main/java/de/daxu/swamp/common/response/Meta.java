package de.daxu.swamp.common.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@SuppressWarnings( "unused" )
public class Meta {

    private int status;
    private String version;
    private Date requestTimestamp;

    private Meta( int status, String version, Date requestTimestamp ) {
        this.status = status;
        this.version = version;
        this.requestTimestamp = requestTimestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getRequestTimestampISO8601() {
        return requestTimestamp;
    }

    public static class Builder {

        private int status;
        private String version;
        private Date timestamp;

        public Builder withSuccess() {
            this.timestamp = new Date();
            this.status = 200;
            return this;
        }

        public Builder withCreated() {
            this.timestamp = new Date();
            this.status = 201;
            return this;
        }

        public Builder withBadRequest() {
            this.timestamp = new Date();
            this.status = 400;
            return this;
        }

        public Builder withForbidden() {
            this.timestamp = new Date();
            this.status = 403;
            return this;
        }

        public Builder withServerError() {
            this.timestamp = new Date();
            this.status = 500;
            return this;
        }

        public Builder withNotFound() {
            this.timestamp = new Date();
            this.status = 404;
            return this;
        }

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Meta build() {
            return new Meta( status, version, timestamp );
        }
    }
}