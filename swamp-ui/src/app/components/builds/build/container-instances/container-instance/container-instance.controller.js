class ContainerInstanceController {
    constructor(BuildService, $interval) {
        this.interval = $interval;
        this.buildService = BuildService;
        this.update(this.initial);
        this.setUpInterval();
    }

    setUpInterval() {
        this.interval(() => this.getInstance(), 5000);
    }

    getInstance() {
        this.buildService.getContainerInstance(this.instance.buildId, this.instance.containerInstanceId)
            .then((instance) => this.update(instance));
    }

    update(instance) {
        this.instance = instance;
        this.log = instance.log;
        this.status = instance.status;
        this.stopReason = instance.stopReason;
        this.removeReason = instance.removeReason;
        this.warnings = (instance.warnings.length > 0) ? instance.warnings.reduce((prev, current) => prev += current) : "";
        this.startedAt = instance.startedAt;
        this.finishedAt = instance.stoppedAt ? instance.stoppedAt : instance.removedAt;
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

export default ['BuildService', '$interval', ContainerInstanceController]