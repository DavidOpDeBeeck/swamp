import MultipleFieldDirective from "../multiple-field.directive.js";
import MultiplePropertyFieldController from "./multiple-property-field.controller.js";

class MultiplePropertyFieldDirective extends MultipleFieldDirective {
    constructor() {
        super({
            extraScope: {
                'nameField': '@',
                'valueField': '@'
            },
            controller: MultiplePropertyFieldController,
            templateUrl: '/assets/templates/multiple-property-field.template.html'
        });
    }
}

export default () => new MultiplePropertyFieldDirective()