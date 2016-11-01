package de.daxu.swamp.common.response;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MetaFactory {

    @Value( "${application.version}" )
    private String version;

    public Meta success() {
        return new Meta.Builder()
                .withSuccess()
                .withVersion( version )
                .build();
    }

    public Meta created() {
        return new Meta.Builder()
                .withCreated()
                .withVersion( version )
                .build();
    }

    public Meta badRequest() {
        return new Meta.Builder()
                .withBadRequest()
                .withVersion( version )
                .build();
    }

    public Meta forbidden() {
        return new Meta.Builder()
                .withForbidden()
                .withVersion( version )
                .build();
    }

    public Meta serverError() {
        return new Meta.Builder()
                .withServerError()
                .withVersion( version )
                .build();
    }

    public Meta notFound() {
        return new Meta.Builder()
                .withNotFound()
                .withVersion( version )
                .build();
    }
}
