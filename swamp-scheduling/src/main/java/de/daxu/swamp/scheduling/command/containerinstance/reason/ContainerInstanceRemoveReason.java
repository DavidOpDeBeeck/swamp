package de.daxu.swamp.scheduling.command.containerinstance.reason;

public enum ContainerInstanceRemoveReason {

    INITIALIZATION_FAILED,
    ERRORS_ON_ACTION,
    NOT_AVAILABLE_ON_HOST,
    STOPPED_WAIT_TIME_EXCEEDED

}
