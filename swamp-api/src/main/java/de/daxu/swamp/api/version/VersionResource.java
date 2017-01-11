package de.daxu.swamp.api.version;

import de.daxu.swamp.api.version.converter.VersionConverter;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static de.daxu.swamp.api.version.VersionResource.VERSION_URL;

@RestController
@RequestMapping( VERSION_URL )
public class VersionResource {

    static final String VERSION_URL = "/version";

    @Value( "${application.version}" )
    private String version;

    private final ResponseFactory responseFactory;
    private final VersionConverter versionConverter;

    @Autowired
    public VersionResource( ResponseFactory responseFactory,
                            VersionConverter versionConverter ) {
        this.responseFactory = responseFactory;
        this.versionConverter = versionConverter;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response get() {

        return responseFactory.success( versionConverter.toDTO( version ) );
    }
}
