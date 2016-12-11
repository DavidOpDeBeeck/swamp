class ProjectEditController {
    constructor(ProjectService, NavigationService, project, $scope) {
        this.$scope = $scope;
        this.project = project;
        this.navigationService = NavigationService;
        this.projectService = ProjectService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('projects');
    }

    edit() {
        this.project.$update()
            .then(() => {
                this.$scope.$close(true);
                this.navigationService.goTo('projects');
            });
    }
}

export default ['ProjectService', 'NavigationService', 'project', '$scope', ProjectEditController]