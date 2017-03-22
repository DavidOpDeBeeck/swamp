import TemplatePickerController from "./template-picker.controller";

class LocationPickerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'containerTemplate': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = TemplatePickerController;
        this.controllerAs = 'templatePicker';
        this.templateUrl = '/assets/templates/template-picker.template.html';
    }
}

export default () => new LocationPickerDirective()