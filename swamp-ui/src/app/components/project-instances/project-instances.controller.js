class ProjectInstancesController {
    constructor(ProjectInstanceService) {
        this.projectInstanceService = ProjectInstanceService;
        this.getAllProjects();
    }

    getAllProjects() {
        this.projectInstanceService.getAllProjects()
            .then((projects) => this.projects = projects);
    }
}

export default ['ProjectInstanceService', ProjectInstancesController]