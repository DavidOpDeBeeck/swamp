class SchedulerContainersController {
    constructor(SchedulerService, project) {
        this.project = project;
        this.schedulerService = SchedulerService;
        this.getProjectContainers();
    }

    getProjectContainers() {
        this.schedulerService.getProjectContainers(this.project.id)
            .then((instances) => this.instances = instances);
    }
}

export default ['SchedulerService', 'project', SchedulerContainersController]