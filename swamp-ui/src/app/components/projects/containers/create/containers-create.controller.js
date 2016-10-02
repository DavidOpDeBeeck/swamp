class ContainersCreateController {
    constructor(ProjectService, LocationService, project, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.project = project;
        this.projectService = ProjectService;
        this.availableLocations = [];
        this.potentialLocations = [undefined];
        LocationService.getAllLocations().then((locations) => this.availableLocations = locations);
    }

    cancel() {
        this.$scope.$dismiss();
        this.$state.go('projects.containers');
    }

    create() {
        this.projectService.createContainer({
            'runConfiguration': this.runConfiguration,
            'potentialLocations': this.potentialLocations,
            'projectId': this.project.id
        }).then((datacenter) => {
            this.$scope.$close(true);
            this.$state.go('projects.containers', {
                projectId: this.project.id,
            }, {reload: true});
        });
    }
}

export default ['ProjectService', 'LocationService', 'project', '$state', '$scope', ContainersCreateController]
