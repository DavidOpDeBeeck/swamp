import FieldDirective from "./../field.directive";

class InputFieldDirective extends FieldDirective {
    constructor() {
        super();
        this.scope['autocompleteList'] = '=';
        this.templateUrl = '/app/field/autocomplete/autocomplete-field.template.html';
    }
}

export default () => new InputFieldDirective()