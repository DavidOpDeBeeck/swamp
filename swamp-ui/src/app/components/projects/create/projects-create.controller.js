class ProjectsCreateController {
    constructor(ProjectService, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.projectService = ProjectService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.$state.go('projects');
    }

    create() {
        this.projectService.createProject({
            'name': this.name,
            'description': this.description
        }).then((project) => {
            this.$scope.$close(true);
            this.$state.go('projects.view', {projectId: project.id}, {reload: true});
        });
    }
}

export default ['ProjectService', '$state', '$scope', ProjectsCreateController]