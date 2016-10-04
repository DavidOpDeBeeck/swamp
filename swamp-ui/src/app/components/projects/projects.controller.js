class ProjectsController {
    constructor(ProjectService) {
        this.projectService = ProjectService;
        this.projects = [];
        this.refresh();
    }

    refresh() {
        this.projectService.getAllProjects()
            .then((projects) => this.projects = projects);
    }

    delete(project) {
        project.$delete()
            .then(() => this.refresh());
    }
}

export default ['ProjectService', ProjectsController]