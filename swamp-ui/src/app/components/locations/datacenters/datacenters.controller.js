class DatacentersController {
    constructor(LocationService, continent) {
        this.continent = continent;
        this.locationService = LocationService;
        this.getAllDatacenters();
    }

    getAllDatacenters() {
        this.locationService.getAllDatacenters(this.continent.id)
            .then((datacenters) => this.datacenters = datacenters);
    }

    delete(datacenter) {
        datacenter.$delete().then(() => this.getAllDatacenters());
    }
}

export default ['LocationService', 'continent', DatacentersController]
