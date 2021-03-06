import ConfigurationPickerController from "./configuration-picker.controller";

class ConfigurationPickerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'configuration': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = ConfigurationPickerController;
        this.controllerAs = 'configurationPicker';
        this.templateUrl = '/assets/templates/configuration-picker.template.html';
    }
}

export default () => new ConfigurationPickerDirective()