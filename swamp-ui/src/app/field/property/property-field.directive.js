import FieldDirective from "./../field.directive";

class PropertyFieldDirective extends FieldDirective {
    constructor() {
        super();
        this.scope['nameField'] = '@';
        this.scope['valueField'] = '@';
        this.templateUrl = '/app/field/property/property-field.template.html';
    }
}

export default () => new PropertyFieldDirective()