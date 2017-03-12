var ansi_up = new AnsiUp;

class ContainerInstanceController {
    constructor(NotificationService, BuildService) {
        this.buildService = BuildService;
        this.notificationService = NotificationService;
        this.initialize();
        this.initializeListeners();
    }

    initialize() {
        this.status = this.initial.status;
        this.startedAt = this.initial.startedAt;
        this.stopReason = this.initial.stopReason;
        this.removeReason = this.initial.removeReason;
        this.finishedAt = this.initial.stoppedAt
            ? this.initial.stoppedAt : this.initial.removedAt;
        this.warnings = (this.initial.warnings.length > 0)
            ? this.initial.warnings.reduce((prev, current) => prev += current) : "";

        this.buildService.getContainerInstanceLogs(this.initial.buildId, this.initial.containerInstanceId)
            .then(logs => {
                this.creationLog = logs.creationLog ? this.handleLog(logs.creationLog) : "";
                this.runningLog = logs.runningLog ? this.handleLog(logs.runningLog) : "";
            });
    }


    handleLog(log) {
        return ansi_up.ansi_to_html(log);
    }

    initializeListeners() {
        this.notificationService.on({
            eventTypes: ['ContainerInstanceStoppedSucceededEvent', 'ContainerInstanceStoppedFailedEvent'],
            identifier: event => event.containerInstanceId === this.initial.containerInstanceId,
            callback: event => this.onContainerStopped(event)
        });
        this.notificationService.on({
            eventTypes: ['ContainerInstanceRemovedSucceededEvent', 'ContainerInstanceRemovedFailedEvent'],
            identifier: event => event.containerInstanceId === this.initial.containerInstanceId,
            callback: event => this.onContainerRemoved(event)
        });
        this.notificationService.on({
            eventTypes: ['ContainerInstanceCreationLogReceivedEvent'],
            identifier: event => event.containerInstanceId === this.initial.containerInstanceId,
            callback: event => this.creationLog += this.handleLog(event.log)
        });
        this.notificationService.on({
            eventTypes: ['ContainerInstanceRunningLogReceivedEvent'],
            identifier: event => event.containerInstanceId === this.initial.containerInstanceId,
            callback: event => this.runningLog += this.handleLog(event.log)
        });
    }

    onContainerStopped(event) {
        this.status = 'STOPPED';
        this.stopReason = event.reason;
        this.finishedAt = event.eventMetaData.createdAt;
        this.warnings = (event.warnings && event.warnings.length > 0)
            ? event.warnings.reduce((prev, current) => prev += current) : "";
    }

    onContainerRemoved(event) {
        this.status = 'REMOVED';
        this.removeReason = event.reason;
        this.finishedAt = event.eventMetaData.createdAt;
        this.warnings = (event.warnings && event.warnings.length > 0)
            ? event.warnings.reduce((prev, current) => prev += current) : "";
    }

    start() {
        this.initial.$start();
    }

    stop() {
        this.initial.$stop();
    }

    restart() {
        this.initial.$restart();
    }
}

export default ['NotificationService', 'BuildService', ContainerInstanceController]