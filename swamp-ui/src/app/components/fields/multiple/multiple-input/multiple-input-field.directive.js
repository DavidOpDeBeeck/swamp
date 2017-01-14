import MultipleFieldDirective from "../multiple-field.directive.js";

class MultipleInputFieldDirective extends MultipleFieldDirective {
    constructor() {
        super({
            templateUrl: '/assets/templates/multiple-input-field.template.html'
        });
    }
}

export default () => new MultipleInputFieldDirective()