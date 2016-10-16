import MultipleFieldDirective from "./../multiple-field.directive";
import MultiplePropertyFieldController from "./multiple-property-field.controller";

class MultiplePropertyFieldDirective extends MultipleFieldDirective {
    constructor() {
        super();
        this.scope['nameField'] = '@';
        this.scope['valueField'] = '@';
        this.controller = MultiplePropertyFieldController;
        this.templateUrl = '/app/field/multiple-property/multiple-property-field.template.html';
    }
}

export default () => new MultiplePropertyFieldDirective()