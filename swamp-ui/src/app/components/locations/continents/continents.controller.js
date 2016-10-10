class ContinentsController {
    constructor(LocationService, NavigationService) {
        this.locationService = LocationService;
        this.navigationService = NavigationService;
        this.getAllContinents();
    }

    getAllContinents() {
        this.locationService.getAllContinents()
            .then((continents) => this.continents = continents);
    }

    delete(continent) {
        continent.$delete()
            .then(() => this.getAllContinents())
            .then(() => this.navigationService.goTo('continents'));
    }
}

export default ['LocationService', 'NavigationService', ContinentsController]
