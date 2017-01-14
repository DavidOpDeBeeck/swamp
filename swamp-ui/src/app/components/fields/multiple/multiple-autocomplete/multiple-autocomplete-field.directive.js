import MultipleFieldDirective from "../multiple-field.directive.js";

class MultipleAutocompleteFieldDirective extends MultipleFieldDirective {
    constructor() {
        super({
            extraScope: {
                'autocompleteList': '='
            },
            templateUrl: '/assets/templates/multiple-autocomplete-field.template.html'
        });
    }
}

export default () => new MultipleAutocompleteFieldDirective()