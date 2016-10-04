class ProjectsCreateController {
    constructor(ProjectService, NavigationService, $scope) {
        this.project = {};
        this.$scope = $scope;
        this.projectService = ProjectService;
        this.navigationService = NavigationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('projects');
    }

    create() {
        this.projectService.createProject(this.project)
            .then((project) => {
                this.$scope.$close(true);
                this.navigationService.goTo('projects.project.containers', {projectId: project.id});
            });
    }
}

export default ['ProjectService', 'NavigationService', '$scope', ProjectsCreateController]