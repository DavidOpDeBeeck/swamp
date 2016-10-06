class DatacentersCreateController {
    constructor(LocationService, NavigationService, continent, $scope) {
        this.$scope = $scope;
        this.continent = continent;
        this.datacenter = {
            'continentId': this.continent.id,
        };
        this.locationService = LocationService;
        this.navigationService = NavigationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('continents.continent.datacenters');
    }

    create() {
        this.locationService.createDatacenter(this.datacenter)
            .then((datacenter) => {
                this.$scope.$close(true);
                this.navigationService.goTo('continents.continent.datacenters.servers', {
                    continentId: this.continent.id,
                    datacenterId: datacenter.id
                });
            });
    }
}

export default ['LocationService', 'NavigationService', 'continent', '$scope', DatacentersCreateController]
