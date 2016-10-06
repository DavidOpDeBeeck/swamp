class ServersCreateController {
    constructor(LocationService, NavigationService, continent, datacenter, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.continent = continent;
        this.datacenter = datacenter;
        this.server = {
            'continentId': this.continent.id,
            'datacenterId': this.datacenter.id,
        };
        this.locationService = LocationService;
        this.navigationService = NavigationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('continents.continent.datacenters.datacenter.servers');
    }

    create() {
        this.locationService.createServer(this.server)
            .then((server) => {
                this.$scope.$close(true);
                this.navigationService.goTo('continents.continent.datacenters.datacenter.servers', {
                    continentId: this.continent.id,
                    datacenterId: this.datacenter.id
                });
            });
    }
}

export default ['LocationService', 'NavigationService', 'continent', 'datacenter', '$state', '$scope', ServersCreateController]
