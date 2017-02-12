package de.daxu.swamp.scheduling.query.build;

import de.daxu.swamp.scheduling.command.build.BuildId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildQueryService {

    private final BuildViewRepository buildViewRepository;

    @Autowired
    public BuildQueryService(BuildViewRepository buildViewRepository) {
        this.buildViewRepository = buildViewRepository;
    }

    public BuildView getBuildViewById(BuildId buildId) {
        return buildViewRepository.getByBuildId(buildId);
    }
}
