package de.daxu.swamp.scheduling.resource.replay;

import de.daxu.swamp.common.axon.QueryRepository;
import de.daxu.swamp.common.util.SpringUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import org.apache.commons.lang.time.StopWatch;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(ReplayResource.class);

    private final ResponseFactory response;
    private final ReplayingCluster replayableCluster;
    private final ApplicationContext appContext;

    @Autowired
    public ReplayResource(ResponseFactory response, ReplayingCluster replayableCluster, ApplicationContext appContext) {
        this.response = response;
        this.replayableCluster = replayableCluster;
        this.appContext = appContext;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response replayEvents() {
        clearViews();
        replay();
        return response.success();
    }

    private void clearViews() {
        getQueryRepositories().forEach(JpaRepository::deleteAllInBatch);
    }

    private Set<JpaRepository> getQueryRepositories() {
        return appContext.getBeansWithAnnotation(QueryRepository.class).values()
                .stream()
                .map(o -> (JpaRepository) o).collect(Collectors.toSet());
    }

    private void replay() {
        StopWatch stopWatch = new StopWatch();
        logger.info("Starting replay");
        logRegisteredEventListeners();
        stopWatch.start();
        replayableCluster.startReplay();
        stopWatch.stop();
        logger.info("Replay took: {}", stopWatch.toString());
    }

    private void logRegisteredEventListeners() {
        replayableCluster.getMembers()
                .forEach(eventListener ->
                        logger.info("Registering {} for replay", SpringUtils.getTargetClassName(eventListener)));
    }
}
