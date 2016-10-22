class ContainerInstanceController {
    constructor(SchedulerService, $interval) {
        this.interval = $interval;
        this.schedulerService = SchedulerService;
        this.update( this.initial );
        this.setUpInterval();
    }
    
    setUpInterval() {
        this.interval(() => this.getInstance(), 5000);
    }

    getInstance() {
        this.schedulerService.getContainer(this.project.id, this.container.id)
            .then((instance) => this.update(instance));
    }

    update( instance ) {
        this.instance = instance;
        this.logs = instance.logs;
        this.status = instance.status;
        this.container = instance.container;
        this.startedAt = this.formatDate(instance.startedAt);
        this.finishedAt = instance.finishedAt == null ? "not yet finished" : this.formatDate(instance.finishedAt);
    }

    start() { this.instance.$start(); }

    stop() { this.instance.$stop(); }

    restart() { this.instance.$restart(); }
    
    formatDate(ms) {
        return new Date(ms).toISOString().replace("T", " ").replace("Z", "");
    }
}

export default ['SchedulerService', '$interval', ContainerInstanceController]