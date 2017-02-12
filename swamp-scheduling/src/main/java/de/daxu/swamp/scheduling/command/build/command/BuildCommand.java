package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class BuildCommand {

    @TargetAggregateIdentifier
    private BuildId buildId;

    public BuildCommand(BuildId buildId) {
        this.buildId = buildId;
    }

    public BuildId getBuildId() {
        return buildId;
    }
}
