import FieldDirective from "../field.directive";

class SelectFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/assets/templates/select-field.template.html'
        });
    }
}

export default () => new SelectFieldDirective()