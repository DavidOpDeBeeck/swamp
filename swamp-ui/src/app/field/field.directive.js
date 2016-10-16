import FieldController from "./field.controller.js";

class FieldDirective {
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
    }
}

export default FieldDirective