class DatacentersController {
    constructor(LocationService, continent) {
        this.continent = continent;
        this.locationService = LocationService;
        this.refresh();
    }

    refresh() {
        this.locationService.getAllDatacenters(this.continent.id)
            .then((datacenters) => this.list = datacenters);
    }

    delete(datacenter) {
        datacenter.$delete().then(() => this.refresh());
    }
}

export default ['LocationService', 'continent', DatacentersController]
