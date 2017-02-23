package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.scheduling.command.build.BuildStatus;

public interface BuildStatusChangedEvent {

    BuildStatus getBuildStatus();
}
