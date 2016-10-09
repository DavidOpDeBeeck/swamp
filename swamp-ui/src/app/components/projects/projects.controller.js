class ProjectsController {
    constructor(ProjectService) {
        this.projectService = ProjectService;
        this.projects = [];
        this.getAllProjects();
    }

    getAllProjects() {
        this.projectService.getAllProjects()
            .then((projects) => this.projects = projects);
    }

    deploy(project) {
        project.$deploy()
            .then(() => this.getAllProjects());
    }

    delete(project) {
        project.$delete()
            .then(() => this.getAllProjects());
    }
}

export default ['ProjectService', ProjectsController]