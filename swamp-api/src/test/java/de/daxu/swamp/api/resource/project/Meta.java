package de.daxu.swamp.api.resource.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private int status;
    private String version;
    private Date requestTimestamp;
    private String location;

    public Meta() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp( Date requestTimestamp ) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }
}
