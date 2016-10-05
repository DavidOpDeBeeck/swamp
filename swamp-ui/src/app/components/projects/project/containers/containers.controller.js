class ContainersController {
    constructor(ProjectService, project) {
        this.project = project;
        this.projectService = ProjectService;
        this.getProjectContainers();
    }

    getProjectContainers() {
        this.projectService.getProjectContainers(this.project.id)
            .then((containers) => this.containers = containers);
    }

    delete(container) {
        container.$delete().then(() => this.getProjectContainers());
    }
}

export default ['ProjectService', 'project', ContainersController]