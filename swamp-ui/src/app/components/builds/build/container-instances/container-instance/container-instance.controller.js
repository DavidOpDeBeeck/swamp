class ContainerInstanceController {
    constructor(BuildService, NotificationService, $timeout) {
        this.buildService = BuildService;
        this.notificationService = NotificationService;
        this.update(this.initial);
        this.notificationService.on({
            eventTypes : ['ContainerInstanceStoppedSucceededEvent', 'ContainerInstanceStoppedFailedEvent'],
            containerInstanceId: this.initial.containerInstanceId,
            callback: event => {
                $timeout(() => {
                    this.status = 'STOPPED';
                    this.stopReason = event.reason;
                    this.finishedAt = event.eventMetaData.createdAt;
                    this.warnings = (event.warnings && event.warnings.length > 0) ?
                        event.warnings.reduce((prev, current) => prev += current) : "";
                });
            }
        });
        this.notificationService.on({
            eventTypes : ['ContainerInstanceRemovedSucceededEvent', 'ContainerInstanceRemovedFailedEvent'],
            containerInstanceId: this.initial.containerInstanceId,
            callback: event => {
                $timeout(() => {
                    this.status = 'REMOVED';
                    this.removeReason = event.reason;
                    this.finishedAt = event.eventMetaData.createdAt;
                    this.warnings = (event.warnings && event.warnings.length > 0) ?
                        event.warnings.reduce((prev, current) => prev += current) : "";
                });
            }
        });
        this.notificationService.on({
            eventTypes : ['ContainerInstanceLogReceivedEvent'],
            containerInstanceId: this.initial.containerInstanceId,
            callback: event => $timeout(() => this.log += event.log, 100)
        });
    }

    getInstance() {
        this.buildService.getContainerInstance(this.instance.buildId, this.instance.containerInstanceId)
            .then((instance) => this.update(instance));
    }

    update(instance) {
        this.instance = instance;
        this.log = instance.log;
        this.status = instance.status;
        this.startedAt = instance.startedAt;
        this.stopReason = instance.stopReason;
        this.removeReason = instance.removeReason;
        this.finishedAt = instance.stoppedAt ? instance.stoppedAt : instance.removedAt;
        this.warnings = (instance.warnings.length > 0) ? instance.warnings.reduce((prev, current) => prev += current) : "";
    }

    start() {
        this.instance.$start();
    }

    stop() {
        this.instance.$stop();
    }

    restart() {
        this.instance.$restart();
    }
}

export default ['BuildService', 'NotificationService', '$timeout', ContainerInstanceController]