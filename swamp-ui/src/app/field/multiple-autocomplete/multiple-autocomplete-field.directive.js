import MultipleFieldDirective from "./../multiple-field.directive";

class MultipleAutocompleteFieldDirective extends MultipleFieldDirective {
    constructor() {
        super();
        this.scope['autocompleteList'] = '=';
        this.templateUrl = '/app/field/multiple-autocomplete/multiple-autocomplete-field.template.html';
    }
}

export default () => new MultipleAutocompleteFieldDirective()