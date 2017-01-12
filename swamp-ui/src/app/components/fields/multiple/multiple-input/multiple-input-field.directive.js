import MultipleFieldDirective from "../multiple-field.directive.js";

class MultipleInputFieldDirective extends MultipleFieldDirective {
    constructor() {
        super({
            templateUrl: '/app/components/fields/multiple/multiple-input/multiple-input-field.template.html'
        });
    }
}

export default () => new MultipleInputFieldDirective()