package de.daxu.swamp.scheduling.query.log;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogQueryService {

    private final LogViewRepository logViewRepository;

    @Autowired
    public LogQueryService(LogViewRepository logViewRepository) {
        this.logViewRepository = logViewRepository;
    }

    public LogView getLogViewById(ContainerInstanceId containerInstanceId) {
        return logViewRepository.getByContainerInstanceId(containerInstanceId);
    }
}
