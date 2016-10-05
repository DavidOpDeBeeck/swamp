class ContinentsController {
    constructor(LocationService) {
        this.locationService = LocationService;
        this.getAllContinents();
    }

    getAllContinents() {
        this.locationService.getAllContinents()
            .then((continents) => this.continents = continents);
    }

    delete(continent) {
        continent.$delete().then(() => this.getAllContinents());
    }
}

export default ['LocationService', ContinentsController]
