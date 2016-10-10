class ServersController {
    constructor(LocationService, NavigationService, continent, datacenter) {
        this.continent = continent;
        this.datacenter = datacenter;
        this.locationService = LocationService;
        this.navigationService = NavigationService;
        this.getAllServers();
    }

    getAllServers() {
        this.locationService.getAllServers(this.continent.id, this.datacenter.id)
            .then((servers) => this.servers = servers);
    }

    delete(server) {
        server.$delete()
            .then(() => this.getAllServers())
            .then(() => this.navigationService.goTo('continents.continent.datacenters.datacenter.servers', {
                continentId: this.continent.id,
                datacenterId: this.datacenter.id
            }));
    }
}

export default ['LocationService', 'NavigationService', 'continent', 'datacenter', ServersController]
