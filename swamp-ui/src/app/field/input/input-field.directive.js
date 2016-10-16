import FieldDirective from "./../field.directive";

class InputFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/app/field/input/input-field.template.html'
        });
    }
}

export default () => new InputFieldDirective()