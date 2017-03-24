package de.daxu.swamp.scheduling.replay;

import com.google.common.base.Stopwatch;
import de.daxu.swamp.common.axon.QueryRepository;
import de.daxu.swamp.common.util.SpringUtils;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReplayService {

    private final Logger logger = LoggerFactory.getLogger(ReplayService.class);

    private final ApplicationContext appContext;
    private final ReplayingCluster replayableCluster;

    @Autowired
    public ReplayService(ApplicationContext appContext, ReplayingCluster replayableCluster) {
        this.appContext = appContext;
        this.replayableCluster = replayableCluster;
    }

    @Transactional
    public void clearViews() {
        getQueryRepositories()
                .forEach(JpaRepository::deleteAllInBatch);
    }

    private Set<JpaRepository> getQueryRepositories() {
        return appContext.getBeansWithAnnotation(QueryRepository.class)
                .values()
                .stream()
                .map(o -> (JpaRepository) o).collect(Collectors.toSet());
    }

    @Transactional
    public void startReplay() {
        logEventListeners(replayableCluster);
        startClusterReplay(replayableCluster);
    }

    private void logEventListeners(ReplayingCluster cluster) {
        cluster.getMembers().stream()
                .map(SpringUtils::getTargetClassName)
                .forEach(listener -> logger.info("Registering {} for replay", listener));
    }

    private void startClusterReplay(ReplayingCluster cluster) {
        Stopwatch stopWatch = Stopwatch.createStarted();
        logger.info("Starting replay");
        try {
            cluster.startReplay();
        } catch (Exception e) {
            logger.warn("Replay failed with exception: {}", e.getCause().getMessage());
            throw e;
        } finally {
            stopWatch.stop();
            logger.info("Replay took: {}", stopWatch.toString());
        }
    }
}
