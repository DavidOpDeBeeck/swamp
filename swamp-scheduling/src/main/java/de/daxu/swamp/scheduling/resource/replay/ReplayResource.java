package de.daxu.swamp.scheduling.resource.replay;

import de.daxu.swamp.common.axon.QueryRepository;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduling.resource.replay.ReplayResource.REPLAY_URL;

@RestController
@RequestMapping(REPLAY_URL)
public class ReplayResource {

    final static String REPLAY_URL = "/replay";

    private final ResponseFactory response;
    private final ReplayingCluster eventListeners;
    private final ApplicationContext appContext;

    @Autowired
    public ReplayResource(ResponseFactory response, ReplayingCluster eventListeners, ApplicationContext appContext) {
        this.response = response;
        this.eventListeners = eventListeners;
        this.appContext = appContext;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response replayEvents() {
        clearViews();
        eventListeners.startReplay();
        return response.success();
    }

    private void clearViews() {
        Set<JpaRepository> queryRepositories = getQueryRepositories();
        queryRepositories.forEach(JpaRepository::deleteAllInBatch);
    }

    private Set<JpaRepository> getQueryRepositories() {
        return appContext.getBeansWithAnnotation(QueryRepository.class).values()
                .stream()
                .map(o -> (JpaRepository) o).collect(Collectors.toSet());
    }
}
