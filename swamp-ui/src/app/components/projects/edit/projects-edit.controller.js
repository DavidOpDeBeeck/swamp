class ProjectsEditController {
    constructor(ProjectService, project, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.project = project;
        this.projectService = ProjectService;
        this.initialise();
    }

    initialise() {
        this.name = this.project.name;
        this.description = this.project.description;
    }

    cancel() {
        this.$scope.$dismiss();
        this.$state.go('projects');
    }

    create() {
        this.projectService.updateProject({
            'id': this.project.id,
            'name': this.name,
            'description': this.description
        }).then((project) => {
            this.$scope.$close(true);
            this.$state.go('projects', {projectId: project.id}, {reload: true});
        });
    }
}

export default ['ProjectService', 'project', '$state', '$scope', ProjectsEditController]