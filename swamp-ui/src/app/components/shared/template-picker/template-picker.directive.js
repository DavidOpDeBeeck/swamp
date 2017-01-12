import TemplatePickerController from "./template-picker.controller";

class LocationPickerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'container': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = TemplatePickerController;
        this.controllerAs = 'templatePicker';
        this.templateUrl = '/app/components/shared/template-picker/template-picker.template.html';
    }
}

export default () => new LocationPickerDirective()