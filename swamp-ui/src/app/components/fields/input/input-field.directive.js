import FieldDirective from "../field.directive";

class InputFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/app/components/fields/input/input-field.template.html'
        });
    }
}

export default () => new InputFieldDirective()