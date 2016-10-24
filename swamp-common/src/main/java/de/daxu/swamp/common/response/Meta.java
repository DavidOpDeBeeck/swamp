package de.daxu.swamp.common.response;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@SuppressWarnings( "unused" )
public class Meta {

    private int status;
    private Date requestTimestamp;

    private Meta( int status, Date requestTimestamp ) {
        this.status = status;
        this.requestTimestamp = requestTimestamp;
    }

    public int getStatus() {
        return status;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getRequestTimestampISO8601() {
        return requestTimestamp;
    }

    public static Meta success() {
        return new Meta.Builder().withSuccess().build();
    }

    public static Meta created() {
        return new Meta.Builder().withCreated().build();
    }

    public static Meta badRequest() {
        return new Meta.Builder().withBadRequest().build();
    }

    public static Meta forbidden() {
        return new Meta.Builder().withForbidden().build();
    }

    public static Meta serverError() {
        return new Meta.Builder().withServerError().build();
    }

    public static Meta notFound() {
        return new Meta.Builder().withNotFound().build();
    }

    public static class Builder {

        private int status;
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

        public Meta build() {
            return new Meta( status, timestamp );
        }
    }
}