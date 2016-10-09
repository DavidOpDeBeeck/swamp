class ServerEditController {
    constructor(LocationService, NavigationService, continent, datacenter, server, $scope) {
        this.$scope = $scope;
        this.continent = continent;
        this.datacenter = datacenter;
        this.server = server;
        this.navigationService = NavigationService;
        this.locationService = LocationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.navigationService.goBack('continents.continent.datacenters.datacenter.servers');
    }

    edit() {
        this.server.$update()
            .then((server) => {
                this.$scope.$close(true);
                this.navigationService.goTo('continents.continent.datacenters.datacenter.servers', {
                    continentId: this.continent.id,
                    datacenterId: this.datacenter.id,
                    serverId: server.id
                });
            });
    }
}

export default ['LocationService', 'NavigationService', 'continent', 'datacenter', 'server', '$scope', ServerEditController]