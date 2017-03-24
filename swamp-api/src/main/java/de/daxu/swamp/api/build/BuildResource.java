package de.daxu.swamp.api.build;

import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.scheduling.query.build.BuildView;
import de.daxu.swamp.scheduling.query.project.ProjectQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static de.daxu.swamp.api.build.BuildResource.BUILD_URL;

@RestController
@RequestMapping(BUILD_URL)
public class BuildResource {

    public final static String BUILD_URL = "/builds";

    private final ResponseFactory response;
    private final ProjectQueryService projectQueryService;

    @Autowired
    public BuildResource(ResponseFactory responseFactory,
                         ProjectQueryService projectQueryService) {
        this.response = responseFactory;
        this.projectQueryService = projectQueryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll() {

        return response.success(projectQueryService.getAllProjectViews());
    }

    @RequestMapping(value = "/{buildId}", method = RequestMethod.GET)
    public Response get(@PathVariable("buildId") BuildView view) {

        return response.success(view);
    }
}
