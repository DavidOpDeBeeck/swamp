package de.daxu.swamp.scheduling.resource.containerinstance;

import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.query.build.BuildView;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason.STOPPED_BY_USER;
import static de.daxu.swamp.scheduling.resource.build.BuildResource.BUILD_URL;
import static de.daxu.swamp.scheduling.resource.containerinstance.ContainerInstanceResource.CONTAINER_INSTANCE_URL;

@RestController
@RequestMapping(CONTAINER_INSTANCE_URL)
public class ContainerInstanceResource {

    final static String CONTAINER_INSTANCE_URL = BUILD_URL + "/{buildId}/containerInstances";

    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ResponseFactory response;
    private final ContainerInstanceCommandService containerInstanceCommandService;

    @Autowired
    public ContainerInstanceResource(ResponseFactory responseFactory,
                                     ContainerInstanceQueryService containerInstanceQueryService,
                                     ContainerInstanceCommandService containerInstanceCommandService) {
        this.response = responseFactory;
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll(@PathVariable("buildId") BuildView view) {

        Set<ContainerInstanceView> containers = view.getContainers()
                .stream()
                .map(containerInstanceQueryService::getContainerInstanceViewById)
                .collect(Collectors.toSet());

        return response.success(containers);
    }

    @RequestMapping(value = "/{containerInstanceId}", method = RequestMethod.GET)
    public Response get(@PathVariable("containerInstanceId") ContainerInstanceView view) {

        return response.success(view);
    }

    @RequestMapping(value = "/{containerInstanceId}", params = {"action=start"}, method = RequestMethod.POST)
    public Response start(@PathVariable("containerInstanceId") ContainerInstanceView containerInstanceView) {

        containerInstanceCommandService.start(containerInstanceView.getContainerInstanceId());

        return response.success();
    }

    @RequestMapping(value = "/{containerInstanceId}", params = {"action=stop"}, method = RequestMethod.POST)
    public Response stop(@PathVariable("containerInstanceId") ContainerInstanceView containerInstanceView) {

        containerInstanceCommandService.stop(containerInstanceView.getContainerInstanceId(), STOPPED_BY_USER);

        return response.success();
    }

    @RequestMapping(value = "/{containerInstanceId}", params = {"action=restart"}, method = RequestMethod.POST)
    public Response restart(@PathVariable("containerInstanceId") ContainerInstanceView containerInstanceView) {

        stop(containerInstanceView);
        start(containerInstanceView);

        return response.success();
    }
}
