class SchedulerContainersController {
    constructor(SchedulerService, project, $sce) {
        this.sce = $sce;
        this.project = project;
        this.schedulerService = SchedulerService;
        this.getProjectContainers();
    }

    getProjectContainers() {
        this.schedulerService.getProjectContainers(this.project.id)
            .then((instances) => this.instances = instances.map((instance) => this.mapInstance(instance)));
    }

    refresh(instance) {
        this.schedulerService.getContainer(this.project.id, instance.container.id)
            .then((instance) => this.mapInstance(instance));
    }

    mapInstance(instance) {
        instance.startedAt = this.formatDate(instance.startedAt);
        instance.finishedAt = (instance.finishedAt == null) ? "not yet finished" : this.formatDate(instance.finishedAt);
        instance.logs = this.sce.trustAsHtml(instance.logs.replace(/\n/g, "<br />"));
        return instance;
    }

    formatDate(milis) {
        return new Date(milis).toISOString().replace("T", " ").replace("Z", "");
    }
}

export default ['SchedulerService', 'project', '$sce', SchedulerContainersController]