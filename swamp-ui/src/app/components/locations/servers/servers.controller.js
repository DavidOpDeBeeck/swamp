class ServersController {
    constructor(LocationService, continent, datacenter) {
        this.continent = continent;
        this.datacenter = datacenter;
        this.locationService = LocationService;
        this.getAllServers();
    }

    getAllServers() {
        this.locationService.getAllServers(this.continent.id, this.datacenter.id)
            .then((servers) => this.servers = servers);
    }

    delete(server) {
        server.$delete().then(() => this.getAllServers());
    }
}

export default ['LocationService', 'continent', 'datacenter', ServersController]
