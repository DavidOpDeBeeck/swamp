class LocationPickerController {
    constructor(LocationService) {
        this.locations = !this.locations || this.locations.length == 0 ? this.locations = [] && this.locations.push(undefined) : this.locations;
        this.availableLocations = [];
        LocationService.getAllLocations().then((locations) => this.availableLocations = locations);
    }

    addLocation() {
        this.locations.push(undefined);
    }

    removeLocation(index) {
        this.locations.splice(index, 1);
    }
}

export default ['LocationService', LocationPickerController]