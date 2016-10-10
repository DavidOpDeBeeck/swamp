class ProjectsController {
    constructor(ProjectService, NavigationService) {
        this.navigationService = NavigationService;
        this.projectService = ProjectService;
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
            .then(() => this.getAllProjects())
            .then(() => this.navigationService.goTo('projects'));
    }
}

export default ['ProjectService', 'NavigationService', ProjectsController]