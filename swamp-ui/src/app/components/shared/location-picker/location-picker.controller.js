class LocationPickerController {
    constructor(LocationService) {
        this.locations = this.editable && (!this.locations || this.locations.length == 0) ? [undefined] : this.locations;
        this.locationService = LocationService;
        this.getAllLocations();
    }

    getAllLocations() {
        this.locationService.getAllLocations().then((locations) => this.availableLocations = locations);
    }

    addLocation() {
        this.locations.push(undefined);
    }

    removeLocation(index) {
        this.locations.splice(index, 1);
    }
}

export default ['LocationService', LocationPickerController]