class ContinentsController {
    constructor(LocationService) {
        this.locationService = LocationService;
        this.refresh();
    }

    refresh() {
        this.locationService.getAllContinents()
            .then((continents) => this.list = continents);
    }

    delete(continent) {
        continent.$delete().then(() => this.refresh());
    }
}

export default ['LocationService', ContinentsController]
