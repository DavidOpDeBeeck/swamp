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
        this.container = instance.container;
        this.startedAt = this.formatDate(instance.dateStarted);
        this.finishedAt = instance.dateStopped == null ? "not yet finished" : this.formatDate(instance.dateStopped);
    }

    start() { this.instance.$start(); }

    stop() { this.instance.$stop(); }

    restart() { this.instance.$restart(); }
    
    formatDate(ms) {
        return new Date(ms).toISOString().replace("T", " ").replace("Z", "");
    }
}

export default ['ProjectInstanceService', '$interval', ContainerInstanceController]