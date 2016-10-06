class DatacenterEditController {
    constructor(LocationService, NavigationService, continent, datacenter, $scope) {
        this.$scope = $scope;
        this.continent = continent;
        this.datacenter = datacenter;
        this.navigationService = NavigationService;
        this.locationService = LocationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('continents.continent.datacenters');
    }

    edit() {
        this.locationService.updateDatacenter(this.datacenter)
            .then((datacenter) => {
                this.$scope.$close(true);
                this.navigationService.goTo('continents.continent.datacenters.datacenter.servers', {
                    continentId: this.continent.id,
                    datacenterId: datacenter.id
                });
            });
    }
}

export default ['LocationService', 'NavigationService', 'continent', 'datacenter', '$scope', DatacenterEditController]