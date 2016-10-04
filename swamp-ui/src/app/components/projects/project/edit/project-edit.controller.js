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

    create() {
        this.projectService.updateProject(this.project)
            .then((project) => {
                this.$scope.$close(true);
                this.navigationService.goTo('projects.project.containers', {projectId: project.id});
            });
    }
}

export default ['ProjectService', 'NavigationService', 'project', '$scope', ProjectEditController]