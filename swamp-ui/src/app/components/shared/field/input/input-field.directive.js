import InputFieldController from "./input-field.controller";

class InputFieldDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'value': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = InputFieldController;
        this.controllerAs = 'field';
        this.templateUrl = '/app/components/shared/field/input/input-field.template.html';
    }
}

export default () => new InputFieldDirective()