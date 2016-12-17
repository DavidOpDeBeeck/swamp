package de.daxu.swamp.test.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {

    private Meta meta;
    private T data;

    public Response() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta( Meta meta ) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData( T data ) {
        this.data = data;
    }
}
