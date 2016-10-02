class RunConfigurationDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'configuration': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = 'ConfigurationPickerController';
        this.controllerAs = 'configurationPicker';
        this.templateUrl = '/app/components/shared/configuration-picker/configuration-picker.template.html';
    }
}

export default RunConfigurationDirective