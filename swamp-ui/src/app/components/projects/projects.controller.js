class ProjectsController {
    constructor(ProjectService) {
        this.projectService = ProjectService;
        this.refresh();
    }

    refresh() {
        this.projectService.getAllProjects().then((projects) => this.list = projects);
    }

    delete(project) {
        project.$delete().then(() => this.refresh());
    }
}

export default ['ProjectService', ProjectsController]