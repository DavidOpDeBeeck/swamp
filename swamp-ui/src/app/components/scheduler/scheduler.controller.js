class SchedulerController {
    constructor(SchedulerService) {
        this.schedulerService = SchedulerService;
        this.getAllProjects();
    }

    getAllProjects() {
        this.schedulerService.getAllProjects()
            .then((projects) => this.projects = projects);
    }
}

export default ['SchedulerService', SchedulerController]