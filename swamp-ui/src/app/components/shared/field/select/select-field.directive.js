import SelectFieldController from "./select-field.controller";

class SelectFieldDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'options': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = SelectFieldController;
        this.controllerAs = 'field';
        this.templateUrl = '/app/components/shared/field/select/select-field.template.html';
    }
}

export default () => new SelectFieldDirective()