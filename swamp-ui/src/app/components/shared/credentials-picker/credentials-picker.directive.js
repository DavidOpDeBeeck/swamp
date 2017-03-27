import CredentialsPickerController from "./credentials-picker.controller";

class CredentialsPickerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'credentials': '=',
            'type': '@',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = CredentialsPickerController;
        this.controllerAs = 'credentialsPicker';
        this.templateUrl = '/assets/templates/credentials-picker.template.html';
    }
}

export default () => new CredentialsPickerDirective()