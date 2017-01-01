import FieldDirective from "../field.directive";

class SelectFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/app/components/fields/select/select-field.template.html'
        });
    }
}

export default () => new SelectFieldDirective()