class SchedulerContainersController {
    constructor(SchedulerService, project, $sce) {
        this.sce = $sce;
        this.project = project;
        this.schedulerService = SchedulerService;
        this.getProjectContainers();
    }

    getProjectContainers() {
        this.schedulerService.getProjectContainers(this.project.id)
            .then((instances) => this.instances = instances.map((instance) => {
                instance.startedAt = new Date(instance.startedAt).toISOString().replace("T", " ").replace("Z", "");
                instance.finishedAt = (instance.finishedAt == null) ? "not yet finished" : new Date(instance.finishedAt).toISOString().replace("T", " ").replace("Z", "");
                instance.logs = this.sce.trustAsHtml(instance.logs.replace(/\n/g, "<br />"));
                return instance;
            }));
    }

    refresh(instance) {
        this.schedulerService.getContainer(this.project.id, instance.container.id)
            .then((response) => {
                instance.startedAt = new Date(response.startedAt).toISOString().replace("T", " ").replace("Z", "");
                instance.finishedAt = (response.finishedAt == null) ? "not yet finished" : new Date(response.finishedAt).toISOString().replace("T", " ").replace("Z", "");
                instance.logs = this.sce.trustAsHtml(response.logs.replace(/\n/g, "<br />"));
            });
    }
}

export default ['SchedulerService', 'project', '$sce', SchedulerContainersController]