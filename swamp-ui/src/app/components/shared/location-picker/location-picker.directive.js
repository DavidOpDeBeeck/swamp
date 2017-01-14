import LocationPickerController from "./location-picker.controller";

class LocationPickerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'locations': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = LocationPickerController;
        this.controllerAs = 'locationPicker';
        this.templateUrl = '/assets/templates/location-picker.template.html';
    }
}

export default () => new LocationPickerDirective()