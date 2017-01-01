import MultipleFieldDirective from "../multiple-field.directive.js";

class MultipleAutocompleteFieldDirective extends MultipleFieldDirective {
    constructor() {
        super({
            extraScope: {
                'autocompleteList': '='
            },
            templateUrl: '/app/components/fields/multiple/multiple-autocomplete/multiple-autocomplete-field.template.html'
        });
    }
}

export default () => new MultipleAutocompleteFieldDirective()