class ContainerInstanceController {
    constructor(ProjectInstanceService, $interval) {
        this.interval = $interval;
        this.projectInstanceService = ProjectInstanceService;
        this.update( this.initial );
        this.setUpInterval();
    }
    
    setUpInterval() {
        this.interval(() => this.getInstance(), 5000);
    }

    getInstance() {
        this.projectInstanceService.getContainerInstance(this.projectInstance.projectInstanceId, this.instance.containerInstanceId)
            .then((instance) => this.update(instance));
    }

    update( instance ) {
        this.instance = instance;
        this.log = instance.log;
        this.status = instance.status;
        this.stopReason = instance.stopReason;
        this.removeReason = instance.removeReason;
        this.warnings = instance.warnings;
        this.startedAt = this.formatDate(instance.startedAt);
        this.finishedAt = instance.stoppedAt == null ? "not yet finished" : this.formatDate(instance.stoppedAt);
    }

    start() { this.instance.$start(); }

    stop() { this.instance.$stop(); }

    restart() { this.instance.$restart(); }
    
    formatDate(ms) {
        return new Date(ms).toISOString().replace("T", " ").replace("Z", "");
    }
}

export default ['ProjectInstanceService', '$interval', ContainerInstanceController]