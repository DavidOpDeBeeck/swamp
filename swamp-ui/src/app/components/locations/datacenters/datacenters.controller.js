class DatacentersController {
    constructor(LocationService, NavigationService, continent) {
        this.continent = continent;
        this.locationService = LocationService;
        this.navigationService = NavigationService;
        this.getAllDatacenters();
    }

    getAllDatacenters() {
        this.locationService.getAllDatacenters(this.continent.id)
            .then((datacenters) => this.datacenters = datacenters);
    }

    delete(datacenter) {
        datacenter.$delete()
            .then(() => this.getAllDatacenters())
            .then(() => this.navigationService.goTo('continents.continent.datacenters', {continentId: this.continent.id}));
    }
}

export default ['LocationService', 'NavigationService', 'continent', DatacentersController]
