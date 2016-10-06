class ContinentEditController {
    constructor(LocationService, NavigationService, continent, $scope) {
        this.$scope = $scope;
        this.continent = continent;
        this.navigationService = NavigationService;
        this.locationService = LocationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('continents');
    }

    edit() {
        this.locationService.updateContinent(this.continent)
            .then((continent) => {
                this.$scope.$close(true);
                this.navigationService.goTo('continents.continent.datacenters', {continentId: continent.id});
            });
    }
}

export default ['LocationService', 'NavigationService', 'continent', '$scope', ContinentEditController]