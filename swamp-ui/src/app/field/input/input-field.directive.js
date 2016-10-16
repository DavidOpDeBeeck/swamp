import FieldDirective from "./../field.directive";

class InputFieldDirective extends FieldDirective {
    constructor() {
        super();
        this.templateUrl = '/app/field/input/input-field.template.html';
    }
}

export default () => new InputFieldDirective()