class ContainerEditController {
    constructor(ProjectService, LocationService, NavigationService, project, container, $scope) {
        this.$scope = $scope;
        this.project = project;
        this.container = container;
        this.projectService = ProjectService;
        this.locationService = LocationService;
        this.navigationService = NavigationService;
        this.availableLocations = [];
        this.getAvailableLocations();
    }

    getAvailableLocations() {
        this.locationService.getAllLocations().then((locations) => this.availableLocations = locations);
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('projects.project.containers');
    }

    edit() {
        this.projectService.updateContainer(this.container)
            .then((container) => {
                this.$scope.$close(true);
                this.navigationService.goTo('projects.project.containers', {projectId: this.project.id});
            });
    }
}

export default ['ProjectService', 'LocationService', 'NavigationService', 'project', 'container', '$scope', ContainerEditController]
