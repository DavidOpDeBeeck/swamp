class ContainersCreateController {
    constructor(ProjectService, LocationService, NavigationService, project, $scope) {
        this.$scope = $scope;
        this.project = project;
        this.projectService = ProjectService;
        this.locationService = LocationService;
        this.navigationService = NavigationService;
        this.availableLocations = [];
        this.container = {
            'projectId': this.project.id,
            'aliases': [],
            'runConfiguration': {},
            'potentialLocations': []
        };
        this.getAvailableLocations();
    }

    getAvailableLocations() {
        this.locationService.getAllLocations().then((locations) => this.availableLocations = locations);
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('projects.project.containers');
    }

    create() {
        this.projectService.createContainer(this.container)
            .then((container) => {
                this.$scope.$close(true);
                this.navigationService.goTo('projects.project.containers', {projectId: this.project.id});
            });
    }
}

export default ['ProjectService', 'LocationService', 'NavigationService', 'project', '$scope', ContainersCreateController]
