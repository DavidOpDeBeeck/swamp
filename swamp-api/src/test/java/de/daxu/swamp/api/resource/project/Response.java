package de.daxu.swamp.api.resource.project;

public class Response {

    private Meta meta;
    private Object data;

    public Response() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta( Meta meta ) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData( Object data ) {
        this.data = data;
    }
}
