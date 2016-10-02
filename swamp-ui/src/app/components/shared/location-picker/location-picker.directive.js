class LocationPickerController {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'locations': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = 'LocationPickerController';
        this.controllerAs = 'locationPicker';
        this.templateUrl = '/app/components/shared/location-picker/location-picker.template.html';
    }
}

export default LocationPickerController