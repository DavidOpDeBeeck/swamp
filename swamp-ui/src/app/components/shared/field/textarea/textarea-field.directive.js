import FieldController from "./../field.controller";

class TextareaFieldDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'value': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = FieldController;
        this.controllerAs = 'field';
        this.templateUrl = '/app/components/shared/field/textarea/textarea-field.template.html';
    }
}

export default () => new TextareaFieldDirective()