class ContainersController {
    constructor(ProjectService, project) {
        this.project = project;
        this.projectService = ProjectService;
        this.refresh();
    }

    refresh() {
        this.projectService.getProjectContainers(this.project.id)
            .then((containers) => this.list = containers);
    }

    delete(container) {
        container.$delete().then(() => this.refresh());
    }
}

export default ['ProjectService', 'project', ContainersController]