class ServersController {
    constructor(LocationService, continent, datacenter) {
        this.continent = continent;
        this.datacenter = datacenter;
        this.locationService = LocationService;
        this.refresh();
    }

    refresh() {
        this.locationService.getAllServers(this.continent.id, this.datacenter.id)
            .then((servers) => this.list = servers);
    }

    delete(server) {
        server.$delete().then(() => this.refresh());
    }
}

export default ['LocationService', 'continent', 'datacenter', ServersController]
