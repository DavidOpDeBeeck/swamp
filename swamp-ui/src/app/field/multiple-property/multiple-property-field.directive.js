import MultipleFieldDirective from "./../multiple-field.directive";
import MultiplePropertyFieldController from "./multiple-property-field.controller";

class MultiplePropertyFieldDirective extends MultipleFieldDirective {
    constructor() {
        super({
            extraScope: {
                'nameField': '@',
                'valueField': '@'
            },
            controller: MultiplePropertyFieldController,
            templateUrl: '/app/field/multiple-property/multiple-property-field.template.html'
        });
    }
}

export default () => new MultiplePropertyFieldDirective()