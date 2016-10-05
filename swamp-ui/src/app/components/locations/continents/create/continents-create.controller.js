class ContinentsCreateController {
    constructor(LocationService, NavigationService, $scope) {
        this.$scope = $scope;
        this.continent = {};
        this.locationService = LocationService;
        this.navigationService = NavigationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('continents');
    }

    create() {
        this.locationService.createContinent(this.continent)
            .then((continent) => {
                this.$scope.$close(true);
                this.navigationService.goTo('continents.continent.datacenters', {continentId: continent.id});
            });
    }
}

export default ['LocationService', 'NavigationService', '$scope', ContinentsCreateController]
