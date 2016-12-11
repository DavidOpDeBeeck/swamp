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
            .then(() => {
                this.$scope.$close(true);
                this.navigationService.goTo('projects');
            });
    }
}

export default ['ProjectService', 'NavigationService', '$scope', ProjectsCreateController]