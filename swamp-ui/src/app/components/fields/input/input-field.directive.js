import FieldDirective from "../field.directive";

class InputFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/assets/templates/input-field.template.html'
        });
    }
}

export default () => new InputFieldDirective()